package br.com.tcc.petsus.domain.model.database.veterinary

import br.com.tcc.petsus.domain.model.database.user.types.User
import org.hibernate.Hibernate
import org.hibernate.proxy.HibernateProxy
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "veterinary_clinic")
data class VeterinaryClinic(
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @Column(name = "id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @Column(name = "veterinary_id") val veterinaryId: Long,
    @Column(name = "clinic_id") val clinicId: Long,
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
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , veterinaryId = $veterinaryId , clinicId = $clinicId )"
    }

}
