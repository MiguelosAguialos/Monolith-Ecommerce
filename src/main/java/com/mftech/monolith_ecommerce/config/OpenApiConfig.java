package com.mftech.monolith_ecommerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Monolith E-commerce API",
                version = "0.0.1",
                description = "API REST de um e-commerce simples para gerenciamento de usuarios, produtos, carrinho e pedidos.",
                contact = @Contact(
                        name = "MFTech",
                        email = "support@mftech.com"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Ambiente local")
        }
)
public class OpenApiConfig {
}
