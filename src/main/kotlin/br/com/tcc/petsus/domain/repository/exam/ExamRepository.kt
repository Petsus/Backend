package br.com.tcc.petsus.domain.repository.exam

import br.com.tcc.petsus.domain.model.exam.Exam
import org.springframework.data.jpa.repository.JpaRepository

interface ExamRepository : JpaRepository<Exam, Long>