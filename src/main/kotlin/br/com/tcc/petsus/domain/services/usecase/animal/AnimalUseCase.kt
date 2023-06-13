package br.com.tcc.petsus.domain.services.usecase.animal

import br.com.tcc.petsus.application.model.animal.request.AnimalRequest
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.BaseUseCase
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder

interface AnimalUseCase : BaseUseCase<AnimalRequest> {
    fun registerQrCode(animalId: Long): ProcessResult
    fun unregisterQrCode(qrCode: String)
    fun putImage(file: MultipartFile, id: Long): ProcessResult
    fun getAnimalForTagId(tagId: String, uriComponentsBuilder: UriComponentsBuilder): ProcessResult
    fun find(id: Long, uriComponentsBuilder: UriComponentsBuilder): ProcessResult
    override fun find(id: Long): ProcessResult { TODO("Not yet implemented") }
    fun getImage(animalId: Long): ProcessResult
}