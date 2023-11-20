package br.com.tcc.petsus.api.users.race

import br.com.tcc.petsus.domain.model.api.animal.request.RaceRequest
import br.com.tcc.petsus.domain.services.usecase.animal.race.RaceUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/races")
class RacesController @Autowired constructor(
    @Autowired private val useCase: RaceUseCase
) {
    @GetMapping
    @Cacheable(value = ["listRaces"])
    fun list() = useCase.list().response()

    @GetMapping("specie/{id}")
    fun listForSpecie(@PathVariable("id") id: Long) = useCase.listForSpecie(id).response()

    @PostMapping
    @Transactional
    @CacheEvict(value = ["listRaces"], allEntries = true)
    fun create(@RequestBody @Valid body: RaceRequest, uriBuilder: UriComponentsBuilder) =
        useCase.create(request = body, uriComponentsBuilder = uriBuilder).response()

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long) = useCase.find(id = id).response()
}