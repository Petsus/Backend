package br.com.tcc.petsus.domain.model.user

import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")  val id: Long,
    @Column(name = "name") var name: String,
    @Column(name = "email") var email: String?,
    @Column(name = "user_password") var userPassword: String?,
    @Column(name = "enable") var enable: Boolean,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @Column(name = "phone") var phone: String?,
    @Column(name = "email_verified") var emailVerified: Date?,
    @Column(name = "phone_verified") var phoneVerified: Date?,
    @Column(name = "role") @Enumerated(EnumType.STRING) val role: UserRole,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = role.authority.toMutableList()

    override fun getPassword(): String = userPassword ?: ""

    override fun getUsername(): String = email ?: ""

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
        return this::class.simpleName + "(id = $id , name = $name , email = $email , userPassword = $userPassword , enable = $enable , createdAt = $createdAt , updatedAt = $updatedAt , phone = $phone , emailVerified = $emailVerified , phoneVerified = $phoneVerified , role = $role )"
    }
}