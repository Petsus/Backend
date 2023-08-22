package br.com.tcc.petsus.api.users.address

import br.com.tcc.petsus.domain.services.usecase.address.AddressUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/address"])
class AddressController @Autowired constructor(
    @Autowired private val useCase: AddressUseCase
) {
    @GetMapping
    fun list() = useCase.list().response()

    @PostMapping
    @Transactional
    fun create(@RequestBody @Valid address: br.com.tcc.petsus.domain.model.api.address.request.AddressRequest, uriBuilder: UriComponentsBuilder) =
        useCase.create(element = address, uriBuilder = uriBuilder).response()

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long) =
        useCase.find(id = id).response()

    @PutMapping("/{id}")
    @Transactional
    fun update(@PathVariable("id") id: Long, @RequestBody addressRequest: br.com.tcc.petsus.domain.model.api.address.request.AddressRequest) =
        useCase.update(id = id, element = addressRequest).response()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) =
        useCase.delete(id = id).response()

}