package br.com.tcc.petsus.domain.model.api.clinic.response

import br.com.tcc.petsus.domain.model.database.clinic.ClinicAddress
import com.google.gson.annotations.SerializedName

data class ClinicAddressResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("distance") val distance: Double,
) {
    companion object {
        @JvmStatic
        fun ClinicAddress.response(): br.com.tcc.petsus.domain.model.api.clinic.response.ClinicAddressResponse =
            br.com.tcc.petsus.domain.model.api.clinic.response.ClinicAddressResponse(
                id = id,
                name = name,
                lat = lat,
                lng = lng,
                distance = distance
            )
    }
}