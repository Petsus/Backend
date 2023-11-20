package br.com.tcc.petsus.domain.model.database.user.types

import br.com.tcc.petsus.domain.model.api.user.response.UserResponse
import br.com.tcc.petsus.domain.model.api.user.response.UserResponse.Companion.userResponse
import br.com.tcc.petsus.domain.model.database.user.base.AuthorizationUser
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user")
@PrimaryKeyJoinColumn(name = "authorization_id")
data class User(
    @Column(name = "phone") var phone: String?,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @Column(name = "phone_verified") var phoneVerified: Date?,
    @Column(name = "email_verified") var emailVerified: Date?,
    @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
) : AuthorizationUser() {
    override fun response(): UserResponse = this.userResponse()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , phone = $phone , phoneVerified = $phoneVerified , emailVerified = $emailVerified , name = $name , email = $email , authorizationId = $authorizationId , enable = $enable )"
    }
    
}