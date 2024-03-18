package com.younes.swaggerConfig;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


// To add a new server instance in case we deploy the application to a certain domain
@OpenAPIDefinition(info = @Info(contact = @Contact(name = "Younes SEBBAR", email = "younesesebbar089@gmail.com"),
                                description = "OpenApi documentation for the Back-End of book management store application",
                                title = "OpenAPI Specification - Younes ^_^ ",
                                version = "1.0"),
                    servers = { @Server(description = "Local Environement", url = "http://localhost:8080")})
@SecurityScheme(name = "bearerAuth",
                description = "Jwt Authentication description",
                scheme = "bearer",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}
