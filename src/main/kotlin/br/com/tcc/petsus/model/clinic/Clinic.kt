package br.com.tcc.petsus.model.clinic

import br.com.tcc.petsus.model.address.address.Address
import br.com.tcc.petsus.model.animal.specie.Specie
import br.com.tcc.petsus.model.exam.Exam
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "clinic")
data class Clinic(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    val name: String,
    val createdAt: Date,
    val updatedAt: Date,
    val cnpj: String?,
    val cpf: String?,
    @JsonIgnore @OneToOne(mappedBy = "clinic" ) @PrimaryKeyJoinColumn val address: Address,
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
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt )"
    }
}