package br.com.tcc.petsus.repository

import br.com.tcc.petsus.model.clinic.Clinic
import br.com.tcc.petsus.model.clinic.ClinicAddress
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class ClinicRepository {

    @Autowired
    var entityManager: EntityManager? = null

    @Suppress("UNCHECKED_CAST")
    fun findNear(latitude: Double, longitude: Double, distanceMiller: Double): List<ClinicAddress> {
        val entityManager = this.entityManager ?: throw InternalError()
        return entityManager.createNativeQuery(
            "select c.id as id, c.name as name, a.lat as lat, a.lng as lng, SQRT(POW(69.1 * (:latitude - a.lat), 2) + POW(69.1 * (a.lng - :longitude) * COS(:latitude / 57.3), 2)) as distance " +
                    "from clinic as c inner join address a " +
                    "on a.clinic_id = c.id " +
                    "having distance <= :distanceMiller " +
                    "order by distance",
            ClinicAddress::class.java
        )
            .setParameter("latitude", latitude)
            .setParameter("longitude", longitude)
            .setParameter("distanceMiller", distanceMiller)
            .resultList as List<ClinicAddress>
    }
}

interface ClinicCompleteRepository : JpaRepository<Clinic, Long>