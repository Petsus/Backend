package br.com.tcc.petsus.domain.services.usecase.clinic

import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.util.UriComponentsBuilder

interface ClinicUseCase {
    fun all(lat: Double, lng: Double, distance: Double): ProcessResult
    fun find(id: Long): ProcessResult
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