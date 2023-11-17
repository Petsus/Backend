package br.com.tcc.petsus.domain.model.database.user.types

import br.com.tcc.petsus.domain.model.api.user.response.UserResponse
import br.com.tcc.petsus.domain.model.api.user.response.VeterinaryResponse.Companion.veterinaryResponse
import br.com.tcc.petsus.domain.model.database.user.base.AuthorizationUser
import br.com.tcc.petsus.domain.model.database.veterinary.Specialities
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "veterinary_user")
@PrimaryKeyJoinColumn(name = "authorization_id")
data class VeterinaryUser(
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @Column(name = "crm") val crm: String,
    @Column(name = "id") @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @ManyToMany(fetch = FetchType.LAZY) @JoinTable(
        name = "veterinary_specialities",
        joinColumns = [JoinColumn(name = "veterinary_id")],
        inverseJoinColumns = [JoinColumn(name = "specialities_id")]

    ) val specialities: MutableList<Specialities>,
) : AuthorizationUser() {
    override fun response(): UserResponse = this.veterinaryResponse()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(authorizationId = $authorizationId , name = $name , email = $email , enable = $enable , createdAt = $createdAt , updatedAt = $updatedAt , id = $id )"
    }
}
