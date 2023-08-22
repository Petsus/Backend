package br.com.tcc.petsus.domain.model.api.specie.request

import br.com.tcc.petsus.domain.model.database.animal.Specie
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class SpecieRequest(
    @SerializedName("name") @NotNull @NotBlank val name: String
) {
    companion object {
        @JvmStatic
        fun SpecieRequest.entity(): Specie {
            val currentDate = Date()
            return Specie(
                id = 0,
                createdAt = currentDate,
                updatedAt = currentDate,
                name = name
            )
        }
    }
}
