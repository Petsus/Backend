package br.com.tcc.petsus.domain.model.api.vaccine.request

import br.com.tcc.petsus.domain.model.database.vaccine.Vaccine
import com.google.gson.annotations.SerializedName
import java.util.*

data class VaccineRequest(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
) {
    companion object {
        @JvmStatic
        fun VaccineRequest.entity(createdAt: Date? = null): Vaccine {
            val currentDate = Date()
            return Vaccine(
                id = id,
                createdAt = createdAt ?: currentDate,
                updatedAt = currentDate,
                name = name
            )
        }
    }
}
