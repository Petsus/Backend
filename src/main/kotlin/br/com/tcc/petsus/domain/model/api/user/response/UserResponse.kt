package br.com.tcc.petsus.domain.model.api.user.response

import br.com.tcc.petsus.domain.model.database.user.role.Roles
import br.com.tcc.petsus.domain.model.database.user.types.User
import com.google.gson.annotations.SerializedName
import java.util.*

open class UserResponse(
    @SerializedName("id") open val id: Long,
    @SerializedName("name") open val name: String,
    @SerializedName("email") open val email: String?,
    @SerializedName("enable") open val enable: Boolean,
    @SerializedName("created_at") open val createdAt: Date,
    @SerializedName("updated_at") open val updatedAt: Date,
    @SerializedName("phone") open val phone: String?,
    @SerializedName("email_verified") open val emailVerified: Date?,
    @SerializedName("phone_verified") open val phoneVerified: Date?,
    @SerializedName("role") open val role: List<Roles>,
) {
    companion object {
        @JvmStatic
        fun User.userResponse(): UserResponse {
            return UserResponse(
                id = id,
                name = name,
                email = email,
                enable = enable,
                createdAt = createdAt,
                updatedAt = updatedAt,
                phone = phone,
                phoneVerified = phoneVerified,
                emailVerified = emailVerified,
                role = roles
            )
        }
    }
}
