package br.com.tcc.petsus.model.user

import org.springframework.security.core.GrantedAuthority

private val USER_ROLES = Roles("USER")
private val CLINIC_ROLES = Roles("CLINIC")
private val ADM_ROLES = Roles("ADM")

enum class UserRole(
    vararg val authority: GrantedAuthority
) {
    USER(USER_ROLES), CLINIC(CLINIC_ROLES), ADM(ADM_ROLES);

    fun isRole(authorities: Collection<GrantedAuthority>): Boolean {
        return authority.first().authority == authorities.first().authority
    }
}

private class Roles(
    val type: String
) : GrantedAuthority {
    override fun getAuthority(): String = type
}