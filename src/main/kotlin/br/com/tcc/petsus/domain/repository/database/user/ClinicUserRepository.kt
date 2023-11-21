package br.com.tcc.petsus.domain.repository.database.user

import br.com.tcc.petsus.domain.model.database.user.types.ClinicUser
import org.springframework.data.jpa.repository.JpaRepository

interface ClinicUserRepository : JpaRepository<ClinicUser, Long> {
    fun findByAuthorizationId(authorizationId: Long): ClinicUser
}