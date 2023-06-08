package br.com.tcc.petsus.repository

import br.com.tcc.petsus.model.user.base.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String?): Optional<User>

    @Query("select user from User user where user.email = :key or user.googleId = :key or user.facebookId = :key")
    fun findByEmailOrGoogleIdOrFacebookId(key: String?): Optional<User>

    fun findByGoogleId(id: String?): Optional<User>

}