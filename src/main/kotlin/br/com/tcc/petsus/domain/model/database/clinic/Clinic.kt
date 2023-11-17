package br.com.tcc.petsus.domain.model.database.clinic

import br.com.tcc.petsus.domain.model.database.address.Address
import br.com.tcc.petsus.domain.model.database.animal.Specie
import br.com.tcc.petsus.domain.model.database.exam.Exam
import br.com.tcc.petsus.domain.model.database.user.types.ClinicUser
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "clinic")
data class Clinic(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") val id: Long,
    @Column(name = "name") val name: String,
    @Column(name = "created_at") val createdAt: Date,
    @Column(name = "updated_at") val updatedAt: Date,
    @OneToOne @JoinColumn(name = "adm_user") val admUser: ClinicUser,
    @Column(name = "phone") val phone: String?,
    @Column(name = "site") val site: String?,
    @OneToOne @JoinColumn(name = "address_id") val address: Address,
    @ManyToMany @JoinTable(
        name = "clinic_exam",
        joinColumns = [JoinColumn(name = "clinic_id")],
        inverseJoinColumns = [JoinColumn(name = "exam_id")]
    ) val exams: MutableList<Exam>,
    @ManyToMany @JoinTable(
        name = "clinic_specie",
        joinColumns = [JoinColumn(name = "clinic_id")],
        inverseJoinColumns = [JoinColumn(name = "specie_id")]
    ) val species: MutableList<Specie>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Clinic

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , createdAt = $createdAt , updatedAt = $updatedAt , address = $address )"
    }
}
