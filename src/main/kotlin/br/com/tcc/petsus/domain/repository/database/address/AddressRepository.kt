package br.com.tcc.petsus.domain.repository.database.address

import br.com.tcc.petsus.domain.model.database.address.Address
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface AddressRepository : JpaRepository<Address, Long> {
    fun findByUserId(userId: Long): List<Address>

    @Query("select a from Address a where a.id = :id and a.userId = :userId")
    fun findAddressByIdAndUser(id: Long, userId: Long): Optional<Address>
}