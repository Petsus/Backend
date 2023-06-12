package br.com.tcc.petsus.application.model.auth.request

import com.google.gson.annotations.SerializedName
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class AuthRequest(
    @SerializedName("email") @Email @NotNull val email: String,
    @SerializedName("password") @NotNull @NotBlank @Size(min = 6) val password: String,
) {
    companion object {
        @JvmStatic
        fun AuthRequest.authToken(): UsernamePasswordAuthenticationToken =
            UsernamePasswordAuthenticationToken(email, password)
    }
}
