package br.com.tcc.petsus.application.model.animal.request

import br.com.tcc.petsus.domain.model.animal.Race
import br.com.tcc.petsus.domain.model.animal.Specie
import com.google.gson.annotations.SerializedName
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class RaceRequest(
    @SerializedName("name") @NotEmpty @NotNull val name: String,
    @SerializedName("specieId") @Positive val specieId: Long,
) {
    companion object {
        @JvmStatic
        fun RaceRequest.entity(specie: Specie): Race =
            Race(id = 0, name = name, specie = specie)
    }
}
