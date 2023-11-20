package br.com.tcc.petsus.domain.model.database.user.role

import br.com.tcc.petsus.domain.model.database.user.types.User
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user_roles")
data class UserRoles(
    @Column(name = "roles_id") var rolesId: Long,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "authorization_id") var authorizationId: Long,
    @Column(name = "id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as User

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , rolesId = $rolesId , createdAt = $createdAt , authorizationId = $authorizationId )"
    }

}