package br.com.tcc.petsus.model.address.city

data class CityResponse(
    val id: Long,
    val name: String,
    val ibgeId: Int,
    val stateId: Long
)

fun City.toResponse(): CityResponse =
    CityResponse(id, name, ibgeId, state.id)
