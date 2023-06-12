package br.com.tcc.petsus.domain.services.usecase

import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.util.UriComponentsBuilder

interface BaseUseCase<T> {
    fun list(): ProcessResult
    fun find(id: Long): ProcessResult
    fun delete(id: Long): ProcessResult
    fun update(id: Long, element: T): ProcessResult
    fun create(element: T, uriBuilder: UriComponentsBuilder): ProcessResult
}