package br.com.tcc.petsus.domain.services.usecase.news

import br.com.tcc.petsus.domain.result.ProcessResult

interface NewsUseCase {
    fun list(): ProcessResult
}