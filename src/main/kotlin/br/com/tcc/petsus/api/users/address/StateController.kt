package br.com.tcc.petsus.api.users.address

import br.com.tcc.petsus.domain.model.api.address.request.StateRequest
import br.com.tcc.petsus.domain.services.usecase.address.StateUseCase
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/state"])
class StateController(
    private val useCase: StateUseCase
) {
    @Cacheable(value = ["state"])
    @GetMapping
    fun get() = useCase.list().response()

    @PostMapping
    @CacheEvict(value = ["state"], allEntries = true)
    fun insert(@RequestBody @Valid stateRequest: StateRequest, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.create(stateRequest, uriComponentsBuilder).response()

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long) = useCase.find(id).response()
}