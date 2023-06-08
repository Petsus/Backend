package br.com.tcc.petsus.model.clinic

import br.com.tcc.petsus.model.address.address.Address
import br.com.tcc.petsus.model.animal.specie.Specie
import br.com.tcc.petsus.model.exam.Exam
import java.util.*

data class ClinicDetails(
    val id: Long,
    val name: String,
    val createdAt: Date,
    val updatedAt: Date,
    val address: Address,
//    val species: List<Specie>,
//    val exams: List<Exam>
)

fun Clinic.toDetails(): ClinicDetails {
    return ClinicDetails(
        id = id,
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt,
        address = address,
//        species = species,
//        exams = exams
    )
}
