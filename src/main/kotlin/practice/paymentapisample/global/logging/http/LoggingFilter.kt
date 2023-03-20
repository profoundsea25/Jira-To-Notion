package practice.paymentapisample.global.logging.http

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.StopWatch
import org.springframework.util.StreamUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper
import practice.paymentapisample.global.common.enums.HttpType
import practice.paymentapisample.global.logging.log
import java.io.InputStream
import java.util.*

@Component
internal class LoggingFilter : OncePerRequestFilter() {
    private val log = this.log()

    private val exceptLoggingUri: Set<String> =
        setOf(
            "favicon.ico",
            "swagger",
            "/v3/api-docs", // Swagger 3
            "/actuator/health",
        )

    override fun shouldNotFilter(request: HttpServletRequest): Boolean =
        exceptLoggingUri.any { it in request.requestURI }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val traceId: String = UUID.randomUUID().toString()
        MDC.put("traceId", traceId)
        val stopWatch: StopWatch = StopWatch(traceId).also { it.start() }
        if (isAsyncDispatch(request)) {
            filterChain.doFilter(request, response)
        } else {
            filterWithLogging(
                wrappedRequest = RequestWrapper(request),
                wrappedResponse = ResponseWrapper(response),
                filterChain = filterChain)
        }
        stopWatch.stop()
        log.info("Processing time=${stopWatch.totalTimeMillis} ms")
    }

    private fun filterWithLogging(
        wrappedRequest: HttpServletRequestWrapper,
        wrappedResponse: ContentCachingResponseWrapper,
        filterChain: FilterChain,
    ) = try {
        val uri: String =
            if (wrappedRequest.queryString.isNullOrBlank()) wrappedRequest.requestURI
            else "${wrappedRequest.requestURI}?${wrappedRequest.queryString}"
        log.info("HTTP Request ${wrappedRequest.method} $uri")
        logPayload(
            prefix = HttpType.REQUEST,
            contentType = wrappedRequest.contentType ?: "",
            inputStream = wrappedRequest.inputStream)
        filterChain.doFilter(wrappedRequest, wrappedResponse)
    } finally {
        logPayload(
            prefix = HttpType.RESPONSE,
            contentType = wrappedResponse.contentType ?: "",
            inputStream = wrappedResponse.contentInputStream)
        wrappedResponse.copyBodyToResponse()
    }

    private fun logPayload(
        prefix: HttpType,
        contentType: String,
        inputStream: InputStream,
    ) {
        val inputStreamByteArray: ByteArray = StreamUtils.copyToByteArray(inputStream)
        val payload: String = when {
            !isWriteable(contentType) -> "Binary Content"
            inputStreamByteArray.isNotEmpty() -> String(inputStreamByteArray)
            else -> "" }
        log.info("$prefix payload=$payload content-type=$contentType")
    }

    private fun isWriteable(
        contentType: String
    ): Boolean =
        MediaType.valueOf(contentType) in setOf(
            MediaType.valueOf("text/*"),
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON,
            MediaType.valueOf("application/*+json"),
            MediaType.APPLICATION_XML,
            MediaType.valueOf("application/*+xml"),
            MediaType.MULTIPART_FORM_DATA)
}