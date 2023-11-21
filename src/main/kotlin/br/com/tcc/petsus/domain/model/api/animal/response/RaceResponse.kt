package br.com.tcc.petsus.domain.model.api.animal.response

import br.com.tcc.petsus.domain.model.api.specie.response.SpecieResponse
import br.com.tcc.petsus.domain.model.database.animal.Race
import br.com.tcc.petsus.domain.model.database.animal.Specie
import com.google.gson.annotations.SerializedName

data class RaceResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("specie") val specie: SpecieResponse?
) {
    companion object {
        @JvmStatic
        fun Race.response(specie: SpecieResponse? = null): RaceResponse = RaceResponse(id = id, name = name, specie = specie)
    }
}
