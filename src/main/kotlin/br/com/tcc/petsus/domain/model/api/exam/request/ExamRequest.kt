package br.com.tcc.petsus.domain.model.api.exam.request

import br.com.tcc.petsus.domain.model.database.exam.Exam
import com.google.gson.annotations.SerializedName
import java.util.*

data class ExamRequest(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
) {
    companion object {
        @JvmStatic
        fun ExamRequest.entity(createdAt: Date? = null): Exam {
            val currentDate = Date()
            return Exam(
                id = id,
                createdAt = createdAt ?: currentDate,
                updatedAt = currentDate,
                name = name
            )
        }
    }
}
