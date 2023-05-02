package br.com.tcc.petsus.filter

import br.com.tcc.petsus.repository.UserRepository
import br.com.tcc.petsus.service.security.TokenService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    private val tokenService: TokenService,
    private val repository: UserRepository
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
            return header.substring(7)
        }

    private fun String.authenticate() {
        val userId = tokenService.getUserId(this)
        val user = repository.findById(userId).get()

        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }

}