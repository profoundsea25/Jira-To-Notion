package practice.paymentapisample.global.common.api

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping(
//    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE])
abstract class BaseRestController