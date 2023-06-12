package br.com.tcc.petsus.application.service.usecase.animal

import br.com.tcc.petsus.domain.model.animal.Animal
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.animal.AnimalUseCase
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class AnimalUseCaseImpl : AnimalUseCase {
    override fun list(): ProcessResult {
        TODO("Not yet implemented")
    }

    override fun find(id: Long): ProcessResult {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): ProcessResult {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, element: Animal): ProcessResult {
        TODO("Not yet implemented")
    }

    override fun create(element: Animal, uriBuilder: UriComponentsBuilder): ProcessResult {
        TODO("Not yet implemented")
    }

}