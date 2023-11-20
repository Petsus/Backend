package br.com.tcc.petsus.domain.model.api.address.response

import br.com.tcc.petsus.domain.model.database.address.City
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("ibgeId") val ibgeId: Int,
    @SerializedName("state") val state: StateResponse,
) {
    companion object {
        @JvmStatic
        fun City.response(state: StateResponse): CityResponse =
            CityResponse(id = id, name = name, ibgeId = ibgeId, state = state)
    }
}
