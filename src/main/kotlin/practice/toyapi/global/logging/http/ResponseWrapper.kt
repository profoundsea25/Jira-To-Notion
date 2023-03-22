package practice.toyapi.global.logging.http

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.util.ContentCachingResponseWrapper

class ResponseWrapper(
    response: HttpServletResponse
) : ContentCachingResponseWrapper(response)