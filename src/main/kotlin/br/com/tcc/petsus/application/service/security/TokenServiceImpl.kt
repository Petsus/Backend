package br.com.tcc.petsus.application.service.security

import br.com.tcc.petsus.application.util.cast
import br.com.tcc.petsus.domain.model.api.auth.response.AuthTokenResponse
import br.com.tcc.petsus.domain.model.database.user.base.AuthorizationUser
import br.com.tcc.petsus.domain.services.security.TokenService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenServiceImpl @Autowired constructor(
    @Value("\${jwt.expiration}") private val expiration: String,
    @Value("\${jwt.secret}") private val secretKey: String
) : TokenService {

    override fun getUserId(token: String): Long {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            .body.subject.toLong()
    }

    override fun generateToken(authentication: Authentication): AuthTokenResponse {
        val currentDate = Date()
        val token = Jwts.builder()
            .setIssuer("API Petsus")
            .setSubject(authentication.principal.cast<AuthorizationUser>().authorizationId.toString())
            .setIssuedAt(currentDate)
            .setNotBefore(currentDate)
            .setExpiration(Date(expiration.toLong() + currentDate.time))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()

        return AuthTokenResponse(
            token = token,
            dateExpiration = expiration.toLong() + currentDate.time
        )
    }

    override fun refreshToken(userId: Long): AuthTokenResponse {
        val currentDate = Date()
        val token = Jwts.builder()
            .setIssuer("API Petsus")
            .setSubject(userId.toString())
            .setIssuedAt(currentDate)
            .setExpiration(Date(expiration.toLong() + currentDate.time))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact()

        return AuthTokenResponse(
            token = token,
            dateExpiration = expiration.toLong() + currentDate.time
        )
    }

    override fun isValidToken(token: String?): Boolean {
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