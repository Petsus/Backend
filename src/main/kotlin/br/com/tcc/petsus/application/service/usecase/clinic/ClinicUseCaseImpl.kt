package br.com.tcc.petsus.application.service.usecase.clinic

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.application.util.toMiller
import br.com.tcc.petsus.domain.model.api.clinic.response.ClinicAddressResponse.Companion.response
import br.com.tcc.petsus.domain.model.api.clinic.response.ClinicDetailsResponse.Companion.responseDetails
import br.com.tcc.petsus.domain.model.api.clinic.response.ClinicListResponse
import br.com.tcc.petsus.domain.model.api.clinic.response.ClinicResponse.Companion.response
import br.com.tcc.petsus.domain.model.database.user.types.TownHallUser
import br.com.tcc.petsus.domain.repository.clinic.ClinicRepository
import br.com.tcc.petsus.domain.repository.townhall.TownHallRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.file.StorageService
import br.com.tcc.petsus.domain.services.usecase.clinic.ClinicUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Component
class ClinicUseCaseImpl @Autowired constructor(
    @Autowired private val storageService: StorageService,
    @Autowired private val clinicRepository: ClinicRepository,
    @Autowired private val townHallRepository: TownHallRepository,
) : ClinicUseCase {
    override fun all(lat: Double, lng: Double, distance: Double): ProcessResult {
        val addresses = clinicRepository.findNear(latitude = lat, longitude = lng, distanceMiller = distance.toMiller()).map { clinic -> clinic.response() }
        return ProcessResultImpl.successful(data = addresses)
    }

    override fun find(id: Long): ProcessResult {
        val clinic = clinicRepository.findById(id).takeIf(Optional<*>::isPresent) ?: return ProcessResultImpl.error(error = null, status = HttpStatus.NOT_FOUND)
        return ProcessResultImpl.successful(data = clinic.get().response())
    }

    override fun search(
        page: Long,
        pageSize: Long,
        orderByName: Boolean,
        orderByDate: Boolean,
        query: String,
        uriComponentsBuilder: UriComponentsBuilder
    ): ProcessResult {
        val cityId = when (val user = currentUser) {
            is TownHallUser -> townHallRepository.findByUserId(user.id)
            else -> return ProcessResultImpl.successful(ClinicListResponse(page = page, pageCount = 0L, clinics = emptyList()))
        }

        if (cityId.isEmpty)
            return ProcessResultImpl.successful(ClinicListResponse(page = page, pageCount = 1L, clinics = emptyList()))

        val sort = run {
            if (orderByDate)
                return@run Sort.by("createdAt").descending()
            if (orderByName)
                return@run Sort.by("name").descending()
            return@run Sort.by("id").descending()
        }

        return clinicRepository.findByNameContains(
            query,
            PageRequest.of(page.toInt() - 1, page.toInt(), sort)
        ).run {
            return@run ProcessResultImpl.successful(
                data = ClinicListResponse(
                    page = page,
                    pageCount = this.size.toLong(),
                    clinics = this.map { clinic ->
                        clinic.responseDetails(
                            image = uriComponentsBuilder.path("/clinic/image/{id}")
                                .buildAndExpand(clinic.id)
                                .toString(),
                        )
                    }
                )
            )
        }
    }

    override fun image(uuid: String): ProcessResult {
        if (!storageService.has("clinic/image/$uuid"))
            return ProcessResultImpl.error(status = HttpStatus.NOT_FOUND, error = null)
        return ProcessResultImpl.resource(resource = storageService.get("clinic/image/$uuid"), mediaType = MediaType.IMAGE_JPEG)
    }
}