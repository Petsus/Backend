package br.com.tcc.petsus.application.repository

import br.com.tcc.petsus.domain.model.external.ViaCepCityResponse
import br.com.tcc.petsus.domain.repository.external.ViaCepRepository
import br.com.tcc.petsus.infrastructure.client.ViaCepDataConfiguration
import com.google.gson.Gson
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ViaCepRepositoryImpl(
    private val webClient: RestTemplate,
    private val configuration: ViaCepDataConfiguration
) : ViaCepRepository {
    private val gson: Gson = Gson()

    override fun findCity(cep: String): ViaCepCityResponse {
        val response = webClient.getForEntity(configuration.get(cep), String::class.java)
        if (response.hasBody())
            return gson.fromJson(response.body, ViaCepCityResponse::class.java)
        throw Throwable("Error request: $cep")
    }

}