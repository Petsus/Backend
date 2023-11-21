package br.com.tcc.petsus.domain.repository.database.race

import br.com.tcc.petsus.domain.model.database.animal.Race
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface RaceRepository : JpaRepository<Race, Long> {
    @Query("select r from Race r where r.specie.id = :specieId")
    fun findBySpecie(specieId: Long): List<Race>
}