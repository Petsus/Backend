package br.com.tcc.petsus.model.address.address

import br.com.tcc.petsus.model.address.city.City
import br.com.tcc.petsus.util.getOrThrow
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class AddressRequest {
    var complement: String? = null

    @NotNull
    val lat: Double? = null

    @NotNull
    val lng: Double? = null

    @NotNull
    var number: Int? = null

    @NotNull
    val cityId: Long? = null

    @NotNull
    @NotBlank
    lateinit var address: String

    @Size(min = 5, max = 8)
    lateinit var postalCode: String

    @NotNull
    @NotBlank
    lateinit var neighborhood: String
}

fun AddressRequest.toAddress(
    id: Long = 0,
    city: City,
    userId: Long? = null,
    clinicId: Long? = null,
    createdAt: Date? = null,
    updatedAt: Date? = null
): Address {
    val current = Date()
    return Address(
        id = id,
        createdAt = createdAt ?: current,
        updatedAt = updatedAt ?: current,
        address = address,
        number = number.getOrThrow(),
        complement = complement,
        neighborhood = neighborhood,
        lat = lat.getOrThrow(),
        lng = lng.getOrThrow(),
        city = city,
        userId = userId,
        clinic = null,
        postalCode = postalCode
    )
}