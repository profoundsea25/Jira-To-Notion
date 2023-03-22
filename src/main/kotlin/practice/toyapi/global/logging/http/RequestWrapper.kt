package practice.toyapi.global.logging.http

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.springframework.util.StreamUtils
import java.io.ByteArrayInputStream
import java.io.InputStream

class RequestWrapper(
    request: HttpServletRequest
) : HttpServletRequestWrapper(request) {
    internal val cachedInputStreamByteArray: ByteArray = StreamUtils.copyToByteArray(request.inputStream)

    override fun getInputStream(): ServletInputStream =
        object : ServletInputStream() {
            private val cached: InputStream = ByteArrayInputStream(cachedInputStreamByteArray)

            override fun isFinished(): Boolean = cached.available() == 0

            override fun isReady(): Boolean = true

            override fun setReadListener(listener: ReadListener?) = throw UnsupportedOperationException()

            override fun read(): Int = cached.read()
        }
}