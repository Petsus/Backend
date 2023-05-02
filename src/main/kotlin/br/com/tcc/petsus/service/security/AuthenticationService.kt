package br.com.tcc.petsus.service.security

import br.com.tcc.petsus.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationService : UserDetailsService {

    @Autowired
    private lateinit var repository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        repository.findByEmailOrGoogleIdOrFacebookId(username).run {
            if (isPresent)
                return get()
            throw UsernameNotFoundException("Data invalid")
        }
    }

}