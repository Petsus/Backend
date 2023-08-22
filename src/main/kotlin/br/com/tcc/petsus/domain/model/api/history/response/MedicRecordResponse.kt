package br.com.tcc.petsus.domain.model.api.history.response

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
import java.util.*

data class MedicRecordResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("createdAt") val createdAt: Date,
    @SerializedName("updatedAt") val updatedAt: Date,
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
            createdAt = createdAt,
            updatedAt = updatedAt,
            clinic = clinic?.response(),
            animal = animal.response(uriComponentsBuilder),
            veterinary = null, //veterinary?.user?.name,
            vaccine = vaccine?.response(),
            exam = exam?.response()
        )
    }
}
