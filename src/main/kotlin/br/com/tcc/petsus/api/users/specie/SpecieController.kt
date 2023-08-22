package br.com.tcc.petsus.api.users.specie

import br.com.tcc.petsus.domain.model.api.specie.request.SpecieRequest
import br.com.tcc.petsus.domain.services.usecase.specie.SpecieUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/species")
class SpecieController @Autowired constructor(
    @Autowired private val useCase: SpecieUseCase
) {
    @GetMapping
    @Cacheable(value = ["listSpecies"])
    fun all() = useCase.all().response()

    @PostMapping
    fun create(@RequestBody @Valid body: SpecieRequest, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.create(body, uriComponentsBuilder).response()
}