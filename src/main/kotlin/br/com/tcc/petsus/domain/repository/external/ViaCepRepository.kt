package br.com.tcc.petsus.domain.repository.external

import br.com.tcc.petsus.domain.model.external.ViaCepCityResponse

interface ViaCepRepository {
    @Throws
    fun findCity(cep: String): ViaCepCityResponse
}