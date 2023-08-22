package br.com.tcc.petsus.domain.repository.user

import br.com.tcc.petsus.domain.model.database.user.types.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String?): Optional<User>
}