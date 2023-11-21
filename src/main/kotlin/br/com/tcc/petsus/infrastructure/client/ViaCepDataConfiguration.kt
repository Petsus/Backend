package br.com.tcc.petsus.infrastructure.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class ViaCepDataConfiguration(
    @Value("\${viacep.url}") var url: String,
    @Value("\${viacep.find}") var path: String
) {
    fun get(cep: String): String {
        return url + path.replace("?", cep)
    }
}