package br.com.tcc.petsus.domain.repository.user

import br.com.tcc.petsus.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String?): Optional<User>
}