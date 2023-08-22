package br.com.tcc.petsus.domain.model.database.user.role

import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import javax.persistence.*

@Entity
@Table(name = "roles")
data class Roles(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") var id: Long,
    @Column(name = "name") var name: String,
) : GrantedAuthority {
    override fun getAuthority(): String = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Roles

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name )"
    }
}