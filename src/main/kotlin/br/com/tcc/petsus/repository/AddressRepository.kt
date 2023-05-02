package br.com.tcc.petsus.repository

import br.com.tcc.petsus.model.address.address.Address
import br.com.tcc.petsus.model.address.city.City
import br.com.tcc.petsus.model.address.state.State
import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<City, Long>

interface StateRepository : JpaRepository<State, Long>

interface AddressRepository : JpaRepository<Address, Long> {

    fun findByUserId(userId: Long): List<Address>

    fun findByClinicId(userId: Long): List<Address>

}