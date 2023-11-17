package br.com.tcc.petsus.domain.repository.user

import br.com.tcc.petsus.domain.model.database.user.base.AuthorizationUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthenticationRepository : JpaRepository<AuthorizationUser, Long> {
    fun findByEmail(email: String?): Optional<out AuthorizationUser>
}