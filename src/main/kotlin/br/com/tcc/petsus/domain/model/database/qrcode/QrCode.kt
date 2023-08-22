package br.com.tcc.petsus.domain.model.database.qrcode

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
@Table(name = "qrcode_animal")
data class QrCode(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")  val id: Long,
    @Column(name = "animal_id") val animalId: Long,
    @Column(name = "qr_code") val qrCode: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as QrCode

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , animalId = $animalId , qrCode = $qrCode )"
    }
}
