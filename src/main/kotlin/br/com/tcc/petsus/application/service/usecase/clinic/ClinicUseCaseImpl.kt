package br.com.tcc.petsus.application.service.usecase.clinic

import br.com.tcc.petsus.domain.model.api.clinic.response.ClinicAddressResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.clinic.response.ClinicResponse.Companion.response
import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.toMiller
import br.com.tcc.petsus.domain.repository.clinic.ClinicRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.clinic.ClinicUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
class ClinicUseCaseImpl @Autowired constructor(
    @Autowired private val clinicRepository: ClinicRepository,
) : ClinicUseCase {
    override fun all(lat: Double, lng: Double, distance: Double): ProcessResult {
        val addresses = clinicRepository.findNear(latitude = lat, longitude = lng, distanceMiller = distance.toMiller()).map { clinic -> clinic.response() }
        return ProcessResultImpl.successful(data = addresses)
    }

    override fun find(id: Long): ProcessResult {
        val clinic = clinicRepository.findById(id).takeIf(Optional<*>::isPresent) ?: return ProcessResultImpl.error(error = null, status = HttpStatus.NOT_FOUND)
        return ProcessResultImpl.successful(data = clinic.get().response())
    }
}