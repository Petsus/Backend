package br.com.tcc.petsus.domain.repository.database.address

import br.com.tcc.petsus.domain.model.database.address.City
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CityRepository : JpaRepository<City, Long> {
    fun findByIbgeId(id: Int): Optional<City>
}