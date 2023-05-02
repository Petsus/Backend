package br.com.tcc.petsus.service.security

import br.com.tcc.petsus.model.auth.AuthToken
import br.com.tcc.petsus.model.user.base.User
import br.com.tcc.petsus.util.cast
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService {

    @Value("\${jwt.expiration}")
    private lateinit var expiration: String

    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    fun getUserId(token: String): Long {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            .body.subject.toLong()
    }

    fun generateToken(authentication: Authentication): AuthToken {
        val currentDate = Date()
        val token = Jwts.builder()
            .setIssuer("API Petsus")
            .setSubject(authentication.principal.cast<User>().id.toString())
            .setIssuedAt(currentDate)
            .setExpiration(Date(expiration.toLong() + currentDate.time))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()

        return AuthToken(
            token = token,
            dateExpiration = expiration.toLong() + currentDate.time
        )
    }

    fun refreshToken(userId: Long): AuthToken {
        val currentDate = Date()
        val token = Jwts.builder()
            .setIssuer("API Petsus")
            .setSubject(userId.toString())
            .setIssuedAt(currentDate)
            .setExpiration(Date(expiration.toLong() + currentDate.time))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()

        return AuthToken(
            token = token,
            dateExpiration = expiration.toLong() + currentDate.time
        )
    }

    fun isValidToken(token: String?): Boolean {
        if (token.isNullOrBlank())
            return false

        runCatching {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
        }.onFailure {
            return false
        }

        return true
    }

}