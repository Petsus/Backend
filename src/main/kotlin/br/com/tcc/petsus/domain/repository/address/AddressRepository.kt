package br.com.tcc.petsus.domain.repository.address

import br.com.tcc.petsus.domain.model.address.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long> {
    fun findByUserId(userId: Long): List<Address>
}