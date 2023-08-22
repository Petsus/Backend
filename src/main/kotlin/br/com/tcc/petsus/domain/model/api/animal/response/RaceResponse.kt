package br.com.tcc.petsus.domain.model.api.animal.response

import br.com.tcc.petsus.domain.model.database.animal.Race
import com.google.gson.annotations.SerializedName

data class RaceResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
) {
    companion object {
        @JvmStatic
        fun Race.response(): br.com.tcc.petsus.domain.model.api.animal.response.RaceResponse =
            br.com.tcc.petsus.domain.model.api.animal.response.RaceResponse(id = id, name = name)
    }
}
