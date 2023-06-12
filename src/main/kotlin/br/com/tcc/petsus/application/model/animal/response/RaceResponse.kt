package br.com.tcc.petsus.application.model.animal.response

import br.com.tcc.petsus.domain.model.animal.Race
import com.google.gson.annotations.SerializedName

data class RaceResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
) {
    companion object {
        @JvmStatic
        fun Race.response(): RaceResponse =
            RaceResponse(id = id, name = name)
    }
}
