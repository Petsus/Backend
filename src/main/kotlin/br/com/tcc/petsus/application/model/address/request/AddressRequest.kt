package br.com.tcc.petsus.application.model.address.request

import br.com.tcc.petsus.application.util.getOrThrow
import br.com.tcc.petsus.domain.model.address.Address
import br.com.tcc.petsus.domain.model.address.City
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AddressRequest(
    @SerializedName("lat") @NotNull val lat: Double,
    @SerializedName("lng") @NotNull val lng: Double,
    @SerializedName("number") @NotNull var number: Int,
    @SerializedName("cityId") @NotNull val cityId: Long? = null,
    @SerializedName("complement") var complement: String? = null,
    @SerializedName("address") @NotNull @NotBlank var address: String,
    @SerializedName("neighborhood") @NotNull @NotBlank var neighborhood: String,
    @SerializedName("postalCode") @Size(min = 5, max = 8) var postalCode: String,
) {
    companion object {
        @JvmStatic
        fun AddressRequest.entity(
            city: City,
            userId: Long,
        ): Address {
            val current = Date()
            return Address(
                id = 0,
                createdAt = current,
                updatedAt = current,
                address = address,
                number = number.getOrThrow(),
                complement = complement,
                neighborhood = neighborhood,
                lat = lat.getOrThrow(),
                lng = lng.getOrThrow(),
                city = city,
                userId = userId,
                postalCode = postalCode
            )
        }
    }
}
