package br.com.tcc.petsus.domain.repository.user

import br.com.tcc.petsus.domain.model.database.user.types.VeterinaryUser
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VeterinaryUserRepository : JpaRepository<VeterinaryUser, Long> {
    @Query("select v from VeterinaryUser v where lower(v.name) like lower(:query)")
    fun findByName(query: String, pageable: PageRequest): List<VeterinaryUser>
}