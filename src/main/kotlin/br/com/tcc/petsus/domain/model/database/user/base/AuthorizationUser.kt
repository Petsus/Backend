package br.com.tcc.petsus.domain.model.database.user.base

import br.com.tcc.petsus.domain.model.api.user.response.UserResponse
import br.com.tcc.petsus.domain.model.database.user.role.Roles
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class AuthorizationUser : UserDetails {
    @Column(name = "name")
    open lateinit var name: String

    @Column(name = "email")
    open lateinit var email: String

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "authorization_id")],
        inverseJoinColumns = [JoinColumn(name = "roles_id")]
    )
    open var roles: MutableList<Roles> = mutableListOf()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    open var authorizationId: Long = 0

    @Column(name = "enable")
    open var enable = false

    @Column(name = "password")
    open lateinit var userPassword: String

    abstract fun response(): UserResponse

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = roles
    override fun getPassword(): String = userPassword
    override fun getUsername(): String = email
    override fun isEnabled(): Boolean = enable
    override fun isAccountNonLocked(): Boolean = true
    override fun isAccountNonExpired(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
}