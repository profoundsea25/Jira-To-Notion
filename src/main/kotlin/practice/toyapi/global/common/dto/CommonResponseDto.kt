package practice.toyapi.global.common.dto

import org.springframework.http.HttpStatus

data class CommonResponseDto<T>(
    val code: Int = HttpStatus.OK.value(),
    val message: String = HttpStatus.OK.name,
    val body: T? = null,
) {
    constructor(httpStatus: HttpStatus) : this(code = httpStatus.value(), message = httpStatus.name)
}