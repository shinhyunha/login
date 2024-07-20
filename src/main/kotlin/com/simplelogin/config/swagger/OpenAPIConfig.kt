package com.simplelogin.config.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {
    @Bean
    fun openAPI(): OpenAPI {
        val info: Info = Info()
            .title("간편로그인 연습")
            .version("1.0.0")
            .description("회원 가입 및 정보를 위한 간편 로그인 훈련")

        return OpenAPI()
            .components(Components())
            .info(info)
    }
}