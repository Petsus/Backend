package br.com.tcc.petsus.domain.repository.clinic

import br.com.tcc.petsus.domain.model.clinic.Clinic
import br.com.tcc.petsus.domain.model.clinic.ClinicAddress
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ClinicRepository : JpaRepository<Clinic, Long> {
    @Query(
        value = "select c.id as id, c.name as name, a.lat as lat, a.lng as lng, SQRT(POW(69.1 * (:latitude - a.lat), 2) + POW(69.1 * (a.lng - :longitude) * COS(:latitude / 57.3), 2)) as distance \" +\n" +
                "                    \"from clinic as c inner join address a \" +\n" +
                "                    \"on a.clinic_id = c.id \" +\n" +
                "                    \"having distance <= :distanceMiller \" +\n" +
                "                    \"order by distance",
        nativeQuery = true
    )
    fun findNear(latitude: Double, longitude: Double, distanceMiller: Double): List<ClinicAddress>
}