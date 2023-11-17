package br.com.tcc.petsus.domain.model.api.clinic.response

import br.com.tcc.petsus.domain.model.api.address.response.AddressResponse
import br.com.tcc.petsus.domain.model.api.address.response.AddressResponse.Companion.response
import br.com.tcc.petsus.domain.model.database.animal.Specie
import br.com.tcc.petsus.domain.model.database.clinic.Clinic
import br.com.tcc.petsus.domain.model.database.exam.Exam
import com.google.gson.annotations.SerializedName
import java.util.*

data class ClinicResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("createdAt") val createdAt: Date,
    @SerializedName("updatedAt") val updatedAt: Date,
    @SerializedName("address") val address: AddressResponse,
    @SerializedName("species") val species: List<Specie>,
    @SerializedName("exams") val exams: List<Exam>
) {
    companion object {
        @JvmStatic
        fun Clinic.response(): ClinicResponse =
            ClinicResponse(
                id = id,
                name = name,
                createdAt = createdAt,
                updatedAt = updatedAt,
                address = address.response(),
                species = species.toList(),
                exams = exams.toList()
            )
    }
}