package br.com.tcc.petsus.api.users.address

import br.com.tcc.petsus.domain.model.api.address.request.CityRequest
import br.com.tcc.petsus.domain.services.usecase.address.CityUseCase
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/city"])
class CityController(
    private val useCase: CityUseCase
) {
    @Cacheable(value = ["city"])
    @GetMapping
    fun get() = useCase.list().response()

    @PostMapping
    @CacheEvict(value = ["city"])
    fun insert(@RequestBody @Valid cityRequest: CityRequest, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.create(cityRequest, uriComponentsBuilder).response()

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long) = useCase.find(id).response()
}