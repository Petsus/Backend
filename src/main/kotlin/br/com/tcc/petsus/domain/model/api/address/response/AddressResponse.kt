package br.com.tcc.petsus.domain.model.api.address.response

import br.com.tcc.petsus.domain.model.api.address.response.CityResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.address.response.StateResponse.Companion.response
import br.com.tcc.petsus.domain.model.database.address.Address
import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng")  val lng: Double,
    @SerializedName("number") val number: Long,
    @SerializedName("city") val city: CityResponse,
    @SerializedName("address") val address: String,
    @SerializedName("state")  val state: StateResponse,
    @SerializedName("complement") val complement: String?,
    @SerializedName("postalCode") val postalCode: String?,
    @SerializedName("neighborhood") val neighborhood: String
) {
    companion object {
        @JvmStatic
        fun Address.response(): AddressResponse =
            AddressResponse(
                id = id,
                lat = lat,
                lng = lng,
                number = number,
                address = address,
                city = city.response(state = city.state.response()),
                state = city.state.response(),
                complement = complement,
                postalCode = postalCode,
                neighborhood = neighborhood
            )
    }
}
