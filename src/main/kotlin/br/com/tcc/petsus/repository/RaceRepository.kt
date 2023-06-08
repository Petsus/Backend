package br.com.tcc.petsus.repository

import br.com.tcc.petsus.model.animal.race.Race
import org.springframework.data.jpa.repository.JpaRepository

interface RaceRepository : JpaRepository<Race, Long>