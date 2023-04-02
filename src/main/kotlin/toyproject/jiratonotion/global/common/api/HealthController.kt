package toyproject.jiratonotion.global.common.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import toyproject.jiratonotion.global.common.dto.CommonResponseDto

@RestController
class HealthController : BaseRestController() {

    @GetMapping("/")
    @Operation(summary = "Health Check")
    fun healthCheck(): ResponseEntity<CommonResponseDto<Nothing>> =
        ResponseEntity.ok().body(CommonResponseDto())

}