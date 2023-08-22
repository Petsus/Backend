package br.com.tcc.petsus.domain.model.api.animal.request

import br.com.tcc.petsus.domain.model.database.animal.Race
import br.com.tcc.petsus.domain.model.database.animal.Specie
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

data class RaceRequest(
    @SerializedName("name") @NotEmpty @NotNull val name: String,
    @SerializedName("specieId") @Positive val specieId: Long,
) {
    companion object {
        @JvmStatic
        fun br.com.tcc.petsus.domain.model.api.animal.request.RaceRequest.entity(specie: Specie): Race {
            val currentDate = Date()
            return Race(id = 0, name = name, specie = specie, updatedAt = currentDate, createdAt = currentDate)
        }
    }
}
