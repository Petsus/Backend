package br.com.tcc.petsus.repository

import br.com.tcc.petsus.model.exam.Exam
import br.com.tcc.petsus.model.exam.Vaccine
import org.springframework.data.jpa.repository.JpaRepository

interface ExamRepository : JpaRepository<Exam, Long>

interface VaccineRepository : JpaRepository<Vaccine, Long>