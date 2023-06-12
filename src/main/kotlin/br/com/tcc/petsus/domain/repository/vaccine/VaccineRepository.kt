package br.com.tcc.petsus.domain.repository.vaccine

import br.com.tcc.petsus.domain.model.vaccine.Vaccine
import org.springframework.data.jpa.repository.JpaRepository

interface VaccineRepository : JpaRepository<Vaccine, Long>