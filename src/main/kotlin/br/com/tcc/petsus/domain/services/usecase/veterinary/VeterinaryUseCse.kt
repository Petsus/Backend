package br.com.tcc.petsus.domain.services.usecase.veterinary

import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.util.UriComponentsBuilder

interface VeterinaryUseCse {
    fun search(
        page: Long,
        pageSize: Long,
        orderByName: Boolean,
        orderByDate: Boolean,
        query: String,
        uriComponentsBuilder: UriComponentsBuilder
    ): ProcessResult

    fun image(
        uuid: String
    ): ProcessResult
}