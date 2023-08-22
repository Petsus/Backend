package br.com.tcc.petsus.domain.model.api.exam.response

import br.com.tcc.petsus.domain.model.database.exam.Exam
import com.google.gson.annotations.SerializedName

data class ExamResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
) {
    companion object {
        @JvmStatic
        fun Exam.response(): ExamResponse =
            ExamResponse(
                id = id,
                name = name
            )
    }
}
