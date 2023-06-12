package br.com.tcc.petsus.application.model.user.request

import br.com.tcc.petsus.domain.model.user.User
import br.com.tcc.petsus.domain.model.user.UserRole
import com.google.gson.annotations.SerializedName
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class UserRequest(
    @SerializedName("name") @NotNull @NotEmpty var name: String,
    @SerializedName("email") @NotNull @Email var email: String,
    @SerializedName("password") @NotNull @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!#@\$%&])(?=.*[0-9])(?=.*[a-z]).{8,16}\$") var password: String,
    @SerializedName("phone") @NotNull @Pattern(regexp = "[0-9]{11}") var phone: String
) {
    companion object {
        @JvmStatic
        fun UserRequest.entity(): User {
            val currentDate = Date()

            return User(
                id = 0,
                name = name,
                userPassword = BCryptPasswordEncoder().encode(password),
                email = email,
                enable = true,
                createdAt = currentDate,
                updatedAt = currentDate,
                phone = phone,
                emailVerified = null,
                phoneVerified = null,
                role = UserRole.USER
            )
        }
    }
}
