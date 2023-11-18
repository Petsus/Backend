package br.com.tcc.petsus.application.service.usecase.veterinary

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.model.api.veterinary.response.VeterinaryDetailsResponse.Companion.responseDetails
import br.com.tcc.petsus.domain.model.api.veterinary.response.VeterinaryListResponse
import br.com.tcc.petsus.domain.model.database.user.types.ClinicUser
import br.com.tcc.petsus.domain.model.database.user.types.TownHallUser
import br.com.tcc.petsus.domain.repository.clinic.ClinicRepository
import br.com.tcc.petsus.domain.repository.townhall.TownHallRepository
import br.com.tcc.petsus.domain.repository.user.VeterinaryUserRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.file.StorageService
import br.com.tcc.petsus.domain.services.usecase.veterinary.VeterinaryUseCse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class VeterinaryUseCaseImpl @Autowired constructor(
    @Autowired private val storageService: StorageService,
    @Autowired private val veterinaryRepository: VeterinaryUserRepository,
    @Autowired private val townHallRepository: TownHallRepository,
    @Autowired private val clinicRepository: ClinicRepository,
) : VeterinaryUseCse {
    override fun search(
        page: Long,
        pageSize: Long,
        orderByName: Boolean,
        orderByDate: Boolean,
        query: String,
        uriComponentsBuilder: UriComponentsBuilder
    ): ProcessResult {
        val (clinicId, cityId) = when(val user = currentUser) {
            is ClinicUser -> clinicRepository.findByAdmUser(user.authorizationId).orElse(null)?.id to null
            is TownHallUser -> null to townHallRepository.findByUserId(user.id).orElse(null)?.id
            else -> null to null
        }

        val sort = run {
            if (orderByDate)
                return@run Sort.by("createdAt").descending()
            if (orderByName)
                return@run Sort.by("name").descending()
            return@run Sort.by("id").descending()
        }

        return veterinaryRepository.findByName(
            query = "%$query%",
            pageable = PageRequest.of(page.toInt() - 1, page.toInt(), sort),
            clinicId = clinicId,
            cityId = cityId
        ).run {
            return@run ProcessResultImpl.successful(
                data = VeterinaryListResponse(
                    page = page,
                    pageCount = this.size.toLong(),
                    veterinaries = this.map { veterinary ->
                        veterinary.responseDetails(
                            image = uriComponentsBuilder.path("/veterinary/image/{id}")
                                .buildAndExpand(veterinary.id)
                                .toString(),
                        )
                    }
                )
            )
        }
    }

    override fun image(uuid: String): ProcessResult {
        if (!storageService.has("veterinary/image/$uuid"))
            return ProcessResultImpl.error(status = HttpStatus.NOT_FOUND, error = null)
        return ProcessResultImpl.resource(resource = storageService.get("veterinary/image/$uuid"), mediaType = MediaType.IMAGE_JPEG)
    }
}