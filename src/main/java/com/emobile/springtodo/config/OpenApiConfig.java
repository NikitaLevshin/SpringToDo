package com.emobile.springtodo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Todo API",
                description = "Simple TODO manager",
                version = "1.0",
                contact = @Contact(
                        name = "Nikita Levshin",
                        url = "https://github.com/NikitaLevshin"
                )
        )
)
public class OpenApiConfig {
}
