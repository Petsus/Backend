package br.com.tcc.petsus.api.users.history

import br.com.tcc.petsus.domain.model.api.history.request.HistoryMedicalRequest
import br.com.tcc.petsus.domain.services.usecase.history.HistoryMedicalUseCase
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/history"])
class HistoryController(
    private val useCase: HistoryMedicalUseCase
) {
    @GetMapping
    fun list(uriComponentsBuilder: UriComponentsBuilder) = useCase.list(uriComponentsBuilder).response()

    @PostMapping
    fun insert(@RequestBody @Valid body: HistoryMedicalRequest, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.create(body, uriComponentsBuilder).response()

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable("id") id: Long) = useCase.delete(id).response()
}