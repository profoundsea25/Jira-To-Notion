package prototype.global.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun apiSampleSwagger(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Server Prototype")
                .description("by Haejun Yang")
                .version("v0.0.1")
        )
        .components(
            Components()
                .addSecuritySchemes(
                    "basicScheme",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("basic")
                )
        )
}