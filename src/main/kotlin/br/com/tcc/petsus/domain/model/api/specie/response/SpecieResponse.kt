package br.com.tcc.petsus.domain.model.api.specie.response

import br.com.tcc.petsus.domain.model.database.animal.Specie
import com.google.gson.annotations.SerializedName
import java.util.*

data class SpecieResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("createdAt") val createdAt: Date,
    @SerializedName("updatedAt") val updatedAt: Date,
    @SerializedName("name") val name: String
) {
    companion object {
        @JvmStatic
        fun Specie.response(): SpecieResponse =
            SpecieResponse(
                id = id,
                createdAt = createdAt,
                updatedAt = updatedAt,
                name = name
            )
    }
}
