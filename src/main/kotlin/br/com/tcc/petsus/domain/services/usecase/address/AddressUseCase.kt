package br.com.tcc.petsus.domain.services.usecase.address

import br.com.tcc.petsus.application.model.address.request.AddressRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.BaseUseCase

interface AddressUseCase : BaseUseCase<AddressRequest> {
    fun state(): ProcessResult
    fun city(): ProcessResult
}