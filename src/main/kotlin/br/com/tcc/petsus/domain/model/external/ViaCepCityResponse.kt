package br.com.tcc.petsus.domain.model.external

import com.google.gson.annotations.SerializedName

data class ViaCepCityResponse(
    @SerializedName("cep") val cep: String,
    @SerializedName("logradouro") val address: String,
    @SerializedName("ibge") val ibgeId: String
)
