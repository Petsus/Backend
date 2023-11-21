package br.com.tcc.petsus.domain.model.api.history.response

import br.com.tcc.petsus.application.util.string
import br.com.tcc.petsus.domain.model.api.animal.response.AnimalResponse
import br.com.tcc.petsus.domain.model.api.animal.response.AnimalResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.clinic.response.ClinicResponse
import br.com.tcc.petsus.domain.model.api.clinic.response.ClinicResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.exam.response.ExamResponse
import br.com.tcc.petsus.domain.model.api.exam.response.ExamResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.vaccine.response.VaccineResponse
import br.com.tcc.petsus.domain.model.api.vaccine.response.VaccineResponse.Companion.response
import br.com.tcc.petsus.domain.model.database.history.MedicRecord
import com.google.gson.annotations.SerializedName
import org.springframework.web.util.UriComponentsBuilder

data class MedicRecordResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("clinic") val clinic: ClinicResponse?,
    @SerializedName("animal") val animal: AnimalResponse,
    @SerializedName("veterinary") val veterinary: String?,
    @SerializedName("vaccine") val vaccine: VaccineResponse?,
    @SerializedName("exam") val exam: ExamResponse?,
) {
    companion object {
        @JvmStatic
        fun MedicRecord.response(uriComponentsBuilder: UriComponentsBuilder) = MedicRecordResponse(
            id = id,
            createdAt = createdAt.string(),
            updatedAt = updatedAt.string(),
            clinic = clinic?.response(),
            animal = animal.response(uriComponentsBuilder),
            veterinary = null, //veterinary?.user?.name,
            vaccine = vaccine?.response(),
            exam = exam?.response()
        )
    }
}
