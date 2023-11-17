package br.com.tcc.petsus.domain.model.api.user.response

import br.com.tcc.petsus.domain.model.database.user.role.Roles
import br.com.tcc.petsus.domain.model.database.user.types.AdmUser
import java.util.*

data class AdmResponse(
    override val id: Long,
    override val name: String,
    override val email: String?,
    override val enable: Boolean,
    override val createdAt: Date,
    override val updatedAt: Date,
    override val role: List<Roles>,
) : UserResponse(
    id = id,
    name = name,
    email = email,
    enable = enable,
    createdAt = createdAt,
    updatedAt = updatedAt,
    phone = null,
    emailVerified = null,
    phoneVerified = null,
    role = role
) {
    companion object {
        fun AdmUser.admResponse(): AdmResponse {
            return AdmResponse(
                id = id,
                name = name,
                email = email,
                enable = enable,
                createdAt = createdAt,
                updatedAt = updatedAt,
                role = roles
            )
        }
    }
}