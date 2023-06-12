package br.com.tcc.petsus.domain.repository.race

import br.com.tcc.petsus.domain.model.animal.Race
import org.springframework.data.jpa.repository.JpaRepository

interface RaceRepository : JpaRepository<Race, Long>