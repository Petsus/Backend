package br.com.tcc.petsus.application.model.address.response

import br.com.tcc.petsus.domain.model.address.City
import com.google.gson.annotations.SerializedName

data class CityResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("ibgeId") val ibgeId: Int,
    @SerializedName("stateId") val stateId: Long
) {
    companion object {
        @JvmStatic
        fun City.response(): CityResponse =
            CityResponse(id = id, name = name, ibgeId = ibgeId, stateId = state.id)
    }
}
