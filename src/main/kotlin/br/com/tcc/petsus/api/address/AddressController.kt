package br.com.tcc.petsus.api.address

import br.com.tcc.petsus.application.model.address.request.AddressRequest
import br.com.tcc.petsus.domain.services.usecase.address.AddressUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/address"])
class AddressController @Autowired constructor(
    @Autowired private val useCase: AddressUseCase
) {

    @Cacheable(value = ["state"])
    @GetMapping(value = ["/state"])
    fun state() =
        useCase.state().response()

    @Cacheable(value = ["city"])
    @GetMapping(value = ["/city"])
    fun city() =
        useCase.city().response()

    @GetMapping
    fun list() =
        useCase.list().response()

    @PostMapping
    @Transactional
    fun create(@RequestBody @Valid address: AddressRequest, uriBuilder: UriComponentsBuilder) =
        useCase.create(element = address, uriBuilder = uriBuilder).response()

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long) =
        useCase.find(id = id).response()

    @PutMapping("/{id}")
    @Transactional
    fun update(@PathVariable("id") id: Long, @RequestBody addressRequest: AddressRequest) =
        useCase.update(id = id, element = addressRequest).response()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) =
        useCase.delete(id = id).response()

}