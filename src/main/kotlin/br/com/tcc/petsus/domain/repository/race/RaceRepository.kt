package br.com.tcc.petsus.domain.repository.race

import br.com.tcc.petsus.domain.model.database.animal.Race
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RaceRepository : JpaRepository<Race, Long>