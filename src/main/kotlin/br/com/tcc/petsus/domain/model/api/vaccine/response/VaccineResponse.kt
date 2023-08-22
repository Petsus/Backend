package br.com.tcc.petsus.domain.model.api.vaccine.response

import br.com.tcc.petsus.domain.model.database.vaccine.Vaccine
import com.google.gson.annotations.SerializedName

data class VaccineResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
) {
    companion object {
        @JvmStatic
        fun Vaccine.response(): VaccineResponse =
            VaccineResponse(
                id = id,
                name = name
            )
    }
}