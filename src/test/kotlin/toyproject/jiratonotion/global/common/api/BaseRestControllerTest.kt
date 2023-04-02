package toyproject.jiratonotion.global.common.api

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
sealed class BaseRestControllerTest(
    protected val mockMvc: MockMvc
)

internal fun ResultActions.okAndPrint(): ResultActions =
    andExpect(status().isOk)
        .andExpect(jsonPath("code").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("message").value(HttpStatus.OK.name))
        .andDo(print())
