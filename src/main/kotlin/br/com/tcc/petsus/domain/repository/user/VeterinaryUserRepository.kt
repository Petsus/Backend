package br.com.tcc.petsus.domain.repository.user

import br.com.tcc.petsus.domain.model.database.user.types.VeterinaryUser
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface VeterinaryUserRepository : JpaRepository<VeterinaryUser, Long> {
    @Query("select v from VeterinaryUser v left join VeterinaryClinic vc on vc.veterinaryId = v.authorizationId left join Address ad on ad.userId = v.authorizationId where (lower(v.name) like lower(:query) or vc.clinicId = :clinicId) and (:cityId is null or ad.city.id = :cityId)")
    fun findByName(
        query: String,
        cityId: Long? = null,
        pageable: PageRequest,
        clinicId: Long? = null,
    ): List<VeterinaryUser>
}