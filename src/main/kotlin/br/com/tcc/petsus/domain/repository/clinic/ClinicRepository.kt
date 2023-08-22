package br.com.tcc.petsus.domain.repository.clinic

import br.com.tcc.petsus.domain.model.database.clinic.Clinic
import br.com.tcc.petsus.domain.model.database.clinic.ClinicAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ClinicRepository : JpaRepository<Clinic, Long> {
    @Query(
        value = "SELECT " +
                "c.id AS id, " +
                "c.name AS name, " +
                "a.lat AS lat, " +
                "a.lng AS lng, " +
                "SQRT(POW(69.1 * (:latitude - a.lat), 2) + POW(69.1 * (a.lng - :longitude) * COS(:latitude / 57.3), 2)) AS distance " +
                "FROM clinic c " +
                "INNER JOIN clinic_address ca " +
                "ON ca.clinic_id = c.id " +
                "INNER JOIN address a " +
                "ON a.id = ca.address_id " +
                "HAVING distance <= 50 " +
                "ORDER BY :distanceMiller",
        nativeQuery = true
    )
    fun findNear(latitude: Double, longitude: Double, distanceMiller: Double): List<ClinicAddress>
}