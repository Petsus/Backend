package br.com.tcc.petsus.domain.services.usecase.news

import br.com.tcc.petsus.domain.model.api.news.request.NewsRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder

interface NewsUseCase {
    fun list(): ProcessResult
    fun create(
        image: MultipartFile,
        request: NewsRequest,
        uriBuilder: UriComponentsBuilder
    ): ProcessResult

    fun update(
        id: Long,
        request: NewsRequest,
        image: MultipartFile?,
        uriBuilder: UriComponentsBuilder
    ): ProcessResult

    fun image(
        uuid: String
    ): ProcessResult
}