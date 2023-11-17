package br.com.tcc.petsus.domain.repository.townhall

import br.com.tcc.petsus.domain.model.database.townhall.TownHall
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TownHallRepository : JpaRepository<TownHall, Long> {
    @Query("select th from TownHall th where th.user = :id")
    fun findByUserId(id: Long): Optional<TownHall>
}