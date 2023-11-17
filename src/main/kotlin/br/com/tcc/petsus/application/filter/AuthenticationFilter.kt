package br.com.tcc.petsus.application.filter

import br.com.tcc.petsus.domain.repository.user.AuthenticationRepository
import br.com.tcc.petsus.domain.repository.user.UserRepository
import br.com.tcc.petsus.domain.services.security.TokenService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    private val tokenService: TokenService,
    private val repository: AuthenticationRepository
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        request.token?.also { token ->
            if (tokenService.isValidToken(token))
                token.authenticate()
        }
        filterChain.doFilter(request, response)
    }

    private val HttpServletRequest.token: String?
        get() {
            val header = getHeader("Authorization")
            if (header.isNullOrBlank() || !header.startsWith("Bearer "))
                return null
            return header.substringAfter("Bearer ")
        }

    private fun String.authenticate() {
        val userId = tokenService.getUserId(this)
        val user = repository.findById(userId).get()

        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

}