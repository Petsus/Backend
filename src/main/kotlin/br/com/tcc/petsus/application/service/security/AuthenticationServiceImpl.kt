package br.com.tcc.petsus.application.service.security

import br.com.tcc.petsus.domain.repository.user.AuthenticationRepository
import br.com.tcc.petsus.domain.repository.user.UserRepository
import br.com.tcc.petsus.domain.services.security.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl @Autowired constructor(
    private val repository: AuthenticationRepository,
) : AuthenticationService {
    override fun loadUserByUsername(username: String?): UserDetails {
        repository.findByEmail(username).run {
            if (isPresent)
                return get()
            throw UsernameNotFoundException("Data invalid")
        }
    }
}