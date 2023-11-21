package br.com.tcc.petsus.infrastructure.client

import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class RestConfiguration {
    @Bean
    fun rest(): RestTemplate {
        val client = HttpComponentsClientHttpRequestFactory().apply {
            httpClient = HttpClientBuilder.create().build()
        }
        return RestTemplate(client).apply {
            uriTemplateHandler = DefaultUriBuilderFactory()
        }
    }
}