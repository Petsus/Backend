package br.com.tcc.petsus.domain.model.database.townhall

import br.com.tcc.petsus.domain.model.database.address.City
import br.com.tcc.petsus.domain.model.database.user.types.User
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "town_hall")
data class TownHall(
    @Column(name = "id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @OneToOne @JoinColumn(name = "city_id") var city: City,
    @Column(name = "user_id") var user: Long,
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
        return this::class.simpleName + "(user = $user , city = $city , createdAt = $createdAt , updatedAt = $updatedAt , id = $id )"
    }
}
