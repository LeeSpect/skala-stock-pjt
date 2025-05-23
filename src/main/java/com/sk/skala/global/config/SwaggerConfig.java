package com.sk.skala.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SKALA Stock Console2 API",
                version = "1.0",
                description = "API documentation for SKALA Stock Console2"
        )
)
public class SwaggerConfig {

}

