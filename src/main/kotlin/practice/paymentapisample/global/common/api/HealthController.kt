package practice.paymentapisample.global.common.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import practice.paymentapisample.global.common.dto.CommonResponseDto

@RestController
class HealthController : BaseRestController() {

    @GetMapping("/")
    fun healthCheck(): ResponseEntity<CommonResponseDto<Nothing>> =
        ResponseEntity.ok().body(CommonResponseDto())

}