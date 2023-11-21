package br.com.tcc.petsus.domain.model.database.animal

import br.com.tcc.petsus.domain.model.database.qrcode.QrCode
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "animal")
data class Animal(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") var id: Long,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @Column(name = "name") var name: String,
    @Column(name = "weight") var weight: Double,
    @Column(name = "height") var height: Int,
    @Column(name = "birthday") var birthday: Date?,
    @Column(name = "user_id") var userId: Long,
    @Column(name = "address_id") var addressId: Long,
    @ManyToOne @JoinColumn(name = "race_id") var race: Race,
    @OneToMany(mappedBy = "animalId") val qrCodes: List<QrCode>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Animal

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , name = $name , weight = $weight , height = $height , birthday = $birthday , user = $userId , race = $race )"
    }
}
