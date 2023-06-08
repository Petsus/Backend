package br.com.tcc.petsus.model.address.address

import br.com.tcc.petsus.model.address.city.City

data class AddressUpdate(
    var complement: String? = null,
    var lat: Double? = null,
    var lng: Double? = null,
    var number: Int? = null,
    var cityId: Long? = null,
    var address: String? = null,
    var postalCode: String? = null,
    var neighborhood: String? = null
)

fun AddressUpdate.copyTo(address: Address, city: City) {
    address.complement = complement ?: address.complement
    address.lat = lat ?: address.lat
    address.lng = lng ?: address.lng
    address.number = number ?: address.number
    address.city = city
    address.address = this.address ?: address.address
    address.postalCode = postalCode ?: address.postalCode
    address.neighborhood = neighborhood ?: address.neighborhood
}