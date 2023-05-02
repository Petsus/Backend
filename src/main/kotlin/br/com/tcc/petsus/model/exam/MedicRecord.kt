package br.com.tcc.petsus.model.exam

import br.com.tcc.petsus.model.animal.Animal
import br.com.tcc.petsus.model.clinic.Clinic
import br.com.tcc.petsus.model.user.veterinary.Veterinary
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "medic_record")
data class MedicRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    var createdAt: Date,
    var updatedAt: Date,
    @ManyToOne @JoinColumn(name = "clinic_id") var clinic: Clinic,
    @ManyToOne @JoinColumn(name = "animal_id") var animal: Animal,
    @ManyToOne @JoinColumn(name = "veterinary_id") var veterinary: Veterinary,
    @ManyToOne @JoinColumn(name = "vaccine_id") var vaccine: Vaccine?,
    @ManyToOne @JoinColumn(name = "exam_id") var exam: Exam?,
    var dateExam: Date?,
    var dateVaccine: Date?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MedicRecord

        return id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , clinic = $clinic , animal = $animal , veterinary = $veterinary , vaccine = $vaccine , exam = $exam , dateExam = $dateExam , dateVaccine = $dateVaccine )"
    }
}