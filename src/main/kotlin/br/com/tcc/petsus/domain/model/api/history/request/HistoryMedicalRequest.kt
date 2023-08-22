package br.com.tcc.petsus.domain.model.api.history.request

import br.com.tcc.petsus.domain.model.database.animal.Animal
import br.com.tcc.petsus.domain.model.database.exam.Exam
import br.com.tcc.petsus.domain.model.database.history.MedicRecord
import br.com.tcc.petsus.domain.model.database.vaccine.Vaccine
import com.google.gson.annotations.SerializedName
import java.util.*

data class HistoryMedicalRequest(
    @SerializedName("examsId") val exam: Long?,
    @SerializedName("vaccineId") val vaccine: Long?,
    @SerializedName("veterinary") val veterinary: String,
    @SerializedName("animalId") val animalId: Long,
) {
    companion object {
        @JvmStatic
        fun HistoryMedicalRequest.entity(
            animal: Animal,
            vaccine: Vaccine?,
            exam: Exam?,
            id: Long = 0,
            createdAt: Date? = null
        ): MedicRecord {
            val currentDate = Date()
            return MedicRecord(
                id = id,
                createdAt = createdAt ?: currentDate,
                updatedAt = currentDate,
                clinic = null,
                animal = animal,
                //veterinary = null,
                vaccine = vaccine,
                exam = exam
            )
        }
    }
}