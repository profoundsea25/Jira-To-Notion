package toyproject.jiratonotion.global.common.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

internal class HealthControllerTest(
    @Autowired
    mockMvc: MockMvc
): BaseRestControllerTest(mockMvc) {

    @Test
    @DisplayName("Filter 작동 테스트")
    fun healthCheckWithFilterTest() {
        val url = "/"
        mockMvc
            .perform(
                get(url)
                    .contentType(MediaType.APPLICATION_JSON))
            .okAndPrint()
    }

}