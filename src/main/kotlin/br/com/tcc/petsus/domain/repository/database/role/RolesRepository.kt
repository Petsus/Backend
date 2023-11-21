package br.com.tcc.petsus.domain.repository.database.role

import br.com.tcc.petsus.domain.model.database.user.role.Roles
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RolesRepository : JpaRepository<Roles, Long> {
    @Query("select r from Roles r where r.name = 'USER'")
    fun user(): Roles

    @Query("select r from Roles r where r.name = 'ADMIN'")
    fun admin(): Roles

    @Query("select r from Roles r where r.name = 'CLINIC'")
    fun clinic(): Roles
}