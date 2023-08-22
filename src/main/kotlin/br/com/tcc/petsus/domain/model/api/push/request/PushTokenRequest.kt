package br.com.tcc.petsus.domain.model.api.push.request

import br.com.tcc.petsus.domain.model.database.push.PushToken
import com.google.gson.annotations.SerializedName
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PushTokenRequest(
    @SerializedName("token") @NotNull @NotBlank var token: String
) {
    companion object {
        @JvmStatic
        fun PushTokenRequest.entity(userId: Long): PushToken =
            PushToken(
                id = 0,
                token = token,
                userId = userId
            )
    }
}