package br.com.tcc.petsus.domain.repository.database.exam

import br.com.tcc.petsus.domain.model.database.exam.Exam
import br.com.tcc.petsus.domain.model.database.vaccine.Vaccine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ExamRepository : JpaRepository<Exam, Long> {
    @Query("select v from Exam v where v.name like %:query%")
    fun filter(query: String): List<Exam>
}