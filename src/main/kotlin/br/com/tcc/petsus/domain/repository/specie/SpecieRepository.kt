package br.com.tcc.petsus.domain.repository.specie

import br.com.tcc.petsus.domain.model.database.animal.Specie
import org.springframework.data.jpa.repository.JpaRepository

interface SpecieRepository : JpaRepository<Specie, Long>