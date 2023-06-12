package br.com.tcc.petsus.domain.repository.address

import br.com.tcc.petsus.domain.model.address.City
import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<City, Long>