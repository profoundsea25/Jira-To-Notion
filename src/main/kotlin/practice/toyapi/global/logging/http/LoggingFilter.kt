package practice.toyapi.global.logging.http

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import practice.toyapi.global.common.enums.HttpType
import practice.toyapi.global.logging.log
import java.io.InputStream
import java.util.*

@Component
internal class LoggingFilter : OncePerRequestFilter() {
    private val log = this.log()

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        exceptLoggingUri.any { it in request.requestURI }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        MDC.put("traceId", UUID.randomUUID().toString())
        val stopWatch: StopWatch = StopWatch().also { it.start() }
        filterWithLogging(
            wrappedRequest = ContentCachingRequestWrapper(request),
            wrappedResponse = ContentCachingResponseWrapper(response),
            filterChain = filterChain
        )
        stopWatch.stop()
        log.info("Running time=${stopWatch.totalTimeMillis} ms")
    }

    private fun filterWithLogging(
        wrappedRequest: ContentCachingRequestWrapper,
        wrappedResponse: ContentCachingResponseWrapper,
        filterChain: FilterChain,
    ) = try {
        filterChain.doFilter(wrappedRequest, wrappedResponse)
    } finally {
        val uri: String = getUriForLogging(
            requestUri = wrappedRequest.requestURI,
            queryString = wrappedRequest.queryString
        )
        log.info("HTTP Request ${wrappedRequest.method} $uri")
        logPayload(
            prefix = HttpType.REQUEST,
            contentType = wrappedRequest.contentType,
            inputStream = wrappedRequest.inputStream
        )
        logPayload(
            prefix = HttpType.RESPONSE,
            contentType = wrappedResponse.contentType,
            inputStream = wrappedResponse.contentInputStream
        )
        wrappedResponse.copyBodyToResponse()
    }

    private fun getUriForLogging(
        requestUri: String,
        queryString: String?,
    ): String {
        val sb: StringBuilder = StringBuilder(requestUri)
        if (!queryString.isNullOrBlank()) {
            sb.append("?$queryString")
        }
        return sb.toString()
    }

    private fun logPayload(
        prefix: HttpType,
        contentType: String?,
        inputStream: InputStream,
    ) {
        val inputStreamByteArray: ByteArray = StreamUtils.copyToByteArray(inputStream)
        val payload: String = when {
            !isWriteable(contentType) -> "Binary Content"
            inputStreamByteArray.isNotEmpty() -> String(inputStreamByteArray)
            else -> ""
        }
        if (payload.isNotEmpty()) {
            log.info("$prefix payload=$payload")
        }
    }

    private fun isWriteable(
        contentType: String?
    ): Boolean =
        if (contentType.isNullOrBlank()) {
            true
        } else {
            MediaType.valueOf(contentType) in writableContentType
        }

    companion object {
        private val exceptLoggingUri: Set<String> =
            setOf(
                "favicon.ico",
                "swagger",
                "/v3/api-docs", // Swagger 3
                "/actuator/health",
            )

        private val writableContentType: Set<MediaType> =
            setOf(
                MediaType.valueOf("text/*"),
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON,
                MediaType.valueOf("application/*+json"),
                MediaType.APPLICATION_XML,
                MediaType.valueOf("application/*+xml"),
                MediaType.MULTIPART_FORM_DATA
            )
    }
}