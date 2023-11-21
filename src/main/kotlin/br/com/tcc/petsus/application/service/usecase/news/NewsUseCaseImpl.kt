package br.com.tcc.petsus.application.service.usecase.news

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.model.api.news.request.NewsRequest
import br.com.tcc.petsus.domain.model.api.news.request.NewsRequest.Companion.entity
import br.com.tcc.petsus.domain.model.api.news.response.NewsResponse
import br.com.tcc.petsus.domain.model.api.news.response.NewsResponse.Companion.response
import br.com.tcc.petsus.domain.model.database.user.types.TownHallUser
import br.com.tcc.petsus.domain.model.database.user.types.User
import br.com.tcc.petsus.domain.repository.database.news.NewsRepository
import br.com.tcc.petsus.domain.repository.database.townhall.TownHallRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.file.StorageService
import br.com.tcc.petsus.domain.services.usecase.news.NewsUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import javax.transaction.Transactional

@Component
class NewsUseCaseImpl @Autowired constructor(
    @Autowired private val newsRepository: NewsRepository,
    @Autowired private val townHallRepository: TownHallRepository,
    @Autowired private val storeService: StorageService,
) : NewsUseCase {
    override fun list(): ProcessResult {
        val userId = when (val user = currentUser) {
            is User -> user.authorizationId
            else -> return ProcessResultImpl.successful(data = emptyList<NewsResponse>())
        }

        return ProcessResultImpl.successful(data = newsRepository.list(userId = userId).map { news -> news.response() })
    }

    @Transactional
    override fun create(
        image: MultipartFile,
        request: NewsRequest,
        uriBuilder: UriComponentsBuilder
    ): ProcessResult {
        val city = when (val user = currentUser) {
            is TownHallUser -> townHallRepository.findByUserId(user.id).orElseGet { null }?.city
            else -> null
        }

        val uuid = UUID.randomUUID().toString()
        val news = newsRepository.save(
            request.entity(
                city = city,
                image = uriBuilder.path("news/image/{uuid}")
                    .buildAndExpand(uuid)
                    .toString()
            )
        )
        storeService.save(multipartFile = image, name = "news/image/$uuid")

        return ProcessResultImpl.successful(data = news)
    }

    override fun update(
        id: Long,
        request: NewsRequest,
        image: MultipartFile?,
        uriBuilder: UriComponentsBuilder
    ): ProcessResult {
        val city = when (val user = currentUser) {
            is TownHallUser -> townHallRepository.findByUserId(user.id).orElseGet { null }?.city
            else -> null
        }

        val uuid = UUID.randomUUID().toString()
        val news = newsRepository.save(
            request.entity(
                city = city,
                currentId = id,
                image = uriBuilder.path("news/image/{uuid}")
                    .buildAndExpand(uuid)
                    .toString()
            )
        )

        if (image != null)
            storeService.save(multipartFile = image, name = "news/image/$uuid")

        return ProcessResultImpl.successful(data = news)
    }

    override fun image(uuid: String): ProcessResult {
        if (!storeService.has("news/image/$uuid"))
            return ProcessResultImpl.error(status = HttpStatus.NOT_FOUND, error = null)
        return ProcessResultImpl.resource(resource = storeService.get("news/image/$uuid"), mediaType = MediaType.IMAGE_JPEG)
    }

}