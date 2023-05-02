package br.com.tcc.petsus.model.address.address

import br.com.tcc.petsus.model.address.city.CityResponse
import br.com.tcc.petsus.model.address.city.toResponse
import br.com.tcc.petsus.model.address.state.StateResponse
import br.com.tcc.petsus.model.address.state.toResponse

data class AddressResponse(
    val id: Long,
    val address: String,
    val number: Int,
    val complement: String?,
    val neighborhood: String,
    val lat: Double,
    val lng: Double,
    val city: CityResponse,
    val state: StateResponse,
    val postalCode: String
)

fun Address.toResponse(): AddressResponse =
    AddressResponse(
        id = id,
        address = address,
        number = number,
        complement = complement,
        neighborhood = neighborhood,
        lat = lat,
        lng = lng,
        city = city.toResponse(),
        state = city.state.toResponse(),
        postalCode = postalCode
    )