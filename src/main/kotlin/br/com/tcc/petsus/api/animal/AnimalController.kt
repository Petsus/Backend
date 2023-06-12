package br.com.tcc.petsus.api.animal

import br.com.tcc.petsus.domain.services.usecase.animal.AnimalUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/animal")
class AnimalController @Autowired constructor(
    private val useCase: AnimalUseCase
) {

}