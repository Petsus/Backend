package br.com.tcc.petsus.domain.repository.database.vaccine

import br.com.tcc.petsus.domain.model.database.vaccine.Vaccine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VaccineRepository : JpaRepository<Vaccine, Long> {
    @Query("select v from Vaccine v where v.name like %:query%")
    fun filter(query: String): List<Vaccine>
}