package br.com.tcc.petsus.domain.model.database.history

import br.com.tcc.petsus.domain.model.database.animal.Animal
import br.com.tcc.petsus.domain.model.database.clinic.Clinic
import br.com.tcc.petsus.domain.model.database.exam.Exam
import br.com.tcc.petsus.domain.model.database.vaccine.Vaccine
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "medic_record")
data class MedicRecord(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") var id: Long,
    @Column(name = "created_at") var createdAt: Date,
    @Column(name = "updated_at") var updatedAt: Date,
    @ManyToOne @JoinColumn(name = "clinic_id") var clinic: Clinic?,
    @ManyToOne @JoinColumn(name = "animal_id") var animal: Animal,
//    @ManyToOne @JoinColumn(name = "veterinary_id") var veterinary: Veterinary?,
    @ManyToOne @JoinColumn(name = "vaccine_id") var vaccine: Vaccine?,
    @ManyToOne @JoinColumn(name = "exam_id") var exam: Exam?,
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
        return this::class.simpleName + "(id = $id , createdAt = $createdAt , updatedAt = $updatedAt , clinic = $clinic , animal = $animal , vaccine = $vaccine , exam = $exam)"
    }
}
