package br.com.tcc.petsus.model.user.base

import br.com.tcc.petsus.model.user.UserRole
import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.annotations.SerializedName
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    var name: String,
    var email: String?,
    var userPassword: String?,
    var enable: Boolean,
    var createdAt: Date,
    var updatedAt: Date,
    var phone: String?,
    var emailVerified: Date?,
    var phoneVerified: Date?,
    var googleId: String?,
    var googlePassword: String?,
    var facebookId: String?,
    var facebookPassword: String?,
    @Enumerated(EnumType.STRING) val role: UserRole,
) : UserDetails {
    data class UserResponse(
        @SerializedName("id") val id: Long,
        @SerializedName("name") val name: String,
        @SerializedName("email") val email: String?,
        @SerializedName("phone") val phone: String?,
        @SerializedName("enable") val enable: Boolean,
        @SerializedName("createdAt") val createdAt: Date,
        @SerializedName("updatedAt") val updatedAt: Date,
        @SerializedName("emailVerified") val emailVerified: Date?,
        @SerializedName("phoneVerified") val phoneVerified: Date?,
    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = role.authority.toMutableList()

    override fun getPassword(): String = (userPassword ?: googlePassword) ?: (facebookPassword ?: "")

    override fun getUsername(): String = (email ?: googleId) ?: (facebookId ?: "")

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = enable
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , email = $email , enable = $enable , createdAt = $createdAt , updatedAt = $updatedAt , phone = $phone , emailVerified = $emailVerified , phoneVerified = $phoneVerified , role = $role )"
    }

    fun response(): UserResponse = UserResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        phone = this.phone,
        enable = this.enable,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        emailVerified = this.emailVerified,
        phoneVerified = this.phoneVerified,
    )

}