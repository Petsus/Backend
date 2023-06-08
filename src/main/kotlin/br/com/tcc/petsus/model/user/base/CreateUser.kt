package br.com.tcc.petsus.model.user.base

import br.com.tcc.petsus.model.user.UserRole
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class CreateUser {
    @NotNull
    @NotEmpty
    lateinit var name: String

    @NotNull
    @Email
    lateinit var email: String

    @NotNull
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!#@\$%&])(?=.*[0-9])(?=.*[a-z]).{8,16}\$")
    lateinit var password: String

    @NotNull
    @Pattern(regexp = "[0-9]{11}")
    lateinit var phone: String
}

fun CreateUser.toUser(): User {
    val current = Date()
    return User(
        id = 0,
        name = name,
        email = email,
        userPassword = BCryptPasswordEncoder().encode(password),
        enable = true,
        createdAt = current,
        updatedAt = current,
        role = UserRole.USER,
        phone = phone,
        emailVerified = null,
        phoneVerified = null,
        googleId = null,
        facebookId = null,
        googlePassword = null,
        facebookPassword = null
    )
}