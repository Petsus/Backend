package br.com.tcc.petsus.domain.model.api.user.request

import br.com.tcc.petsus.domain.model.database.user.types.User
import com.google.gson.annotations.SerializedName
import java.util.*
import javax.validation.constraints.Pattern

data class UserUpdateRequest(
    @SerializedName("name") var name: String?,
    @SerializedName("email") var email: String?,
    @SerializedName("phone") @Pattern(regexp = "[0-9]{11}") var phone: String?
) {
    companion object {
        @JvmStatic
        fun UserUpdateRequest.entity(current: User): User {
            val currentDate = Date()
            return current.apply {
                this.updatedAt = currentDate
                this.name = this@entity.name ?: this.name
                this.phone = this@entity.phone ?: this.phone
                this.email = this@entity.name ?: this.email
            }
        }
    }
}