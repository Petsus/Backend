package br.com.tcc.petsus.infrastructure.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfigurations {
    @Bean
    fun api(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Spring API")
                    .description("Spring API Doc")
                    .version("v0.0.1")
                    .license(
                        License().name("Apache 2.0").url("http://springdoc.org")
                    )
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("Spring Wiki Documentation")
                    .url("https://springshop.wiki.github.org/docs")
            )
            .components(
                Components().addSecuritySchemes(
                    "bearer-key",
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .`in`(SecurityScheme.In.HEADER)
                        .bearerFormat("JWT")
                )
            )
    }

}