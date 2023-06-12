package br.com.tcc.petsus.domain.services.usecase.about

import br.com.tcc.petsus.domain.result.ProcessResult

interface AboutUseCase {
    fun about(): ProcessResult
}