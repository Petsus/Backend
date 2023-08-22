package br.com.tcc.petsus.domain.model.api.user.response

import br.com.tcc.petsus.domain.model.database.user.role.Roles
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.persistence.*

data class UserResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String?,
    @SerializedName("enable") var enable: Boolean,
    @SerializedName("created_at") var createdAt: Date,
    @SerializedName("updated_at") var updatedAt: Date,
    @SerializedName("phone") var phone: String?,
    @SerializedName("email_verified") var emailVerified: Date?,
    @SerializedName("phone_verified") var phoneVerified: Date?,
    @SerializedName("role") val role: List<Roles>,
)
