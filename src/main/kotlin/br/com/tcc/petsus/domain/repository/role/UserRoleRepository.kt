package br.com.tcc.petsus.domain.repository.role

import br.com.tcc.petsus.domain.model.database.user.role.UserRoles
import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository: JpaRepository<UserRoles, Long>