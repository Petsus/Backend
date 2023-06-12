package br.com.tcc.petsus.application.model.clinic.response

import br.com.tcc.petsus.application.model.address.response.AddressResponse
import br.com.tcc.petsus.application.model.address.response.AddressResponse.Companion.response
import br.com.tcc.petsus.domain.model.address.Address
import br.com.tcc.petsus.domain.model.animal.Specie
import br.com.tcc.petsus.domain.model.clinic.Clinic
import br.com.tcc.petsus.domain.model.exam.Exam
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