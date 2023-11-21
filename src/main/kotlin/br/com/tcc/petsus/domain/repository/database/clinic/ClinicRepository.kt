package br.com.tcc.petsus.domain.repository.database.clinic

import br.com.tcc.petsus.domain.model.database.clinic.Clinic
import br.com.tcc.petsus.domain.model.database.clinic.ClinicAddress
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ClinicRepository : JpaRepository<Clinic, Long> {
    @Query(
        value = "SELECT " +
                "c.id AS id, " +
                "c.name AS name, " +
                "a.lat AS lat, " +
                "a.lng AS lng, " +
                "SQRT(POW(69.1 * (:latitude - a.lat), 2) + POW(69.1 * (a.lng - :longitude) * COS(:latitude / 57.3), 2)) AS distance " +
                "FROM clinic c " +
                "INNER JOIN address a " +
                "ON a.id = c.address_id " +
                "HAVING distance <= :distanceMiller " +
                "ORDER BY distance",
        nativeQuery = true
    )
    fun findNear(latitude: Double, longitude: Double, distanceMiller: Double): List<ClinicAddress>

    @Query("select clinic from Clinic clinic where clinic.admUser.id = :admUserId")
    fun findByAdmUser(admUserId: Long): Optional<Clinic>

    fun findByNameContains(name: String, pageable: PageRequest): List<Clinic>
}