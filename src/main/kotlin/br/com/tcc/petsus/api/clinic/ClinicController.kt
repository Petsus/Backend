package br.com.tcc.petsus.api.clinic

import br.com.tcc.petsus.domain.services.usecase.clinic.ClinicUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping(value = ["/clinic"])
class ClinicController @Autowired constructor(
    private val useCase: ClinicUseCase
) {
    @GetMapping(value = ["/near"])
    fun all(
        @RequestParam(name = "lat") lat: Double,
        @RequestParam(name = "lng") lng: Double,
        @RequestParam("distance") distance: Double
    ) = useCase.all(lat = lat, lng = lng, distance = distance).response()

    @GetMapping(value = ["/{id}"])
    fun find(@PathVariable(value = "id") id: Long) =
        useCase.find(id = id).response()

    @GetMapping("/search")
    fun search(
        @RequestParam("page", required = false, defaultValue = "1") page: Long,
        @RequestParam("pageSize", required = false, defaultValue = "50") pageSize: Long,
        @RequestParam("orderByName", required = false, defaultValue = "false") orderByName: Boolean,
        @RequestParam("orderByDate", required = false, defaultValue = "false") orderByDate: Boolean,
        @RequestParam("query", required = false, defaultValue = "") query: String,
        uriComponentsBuilder: UriComponentsBuilder
    ) = useCase.search(page, pageSize, orderByName, orderByDate, query, uriComponentsBuilder).response()

    @GetMapping("/image/{id}")
    fun image(
        @PathVariable("id") uuid: String
    ) = useCase.image(uuid).response()
}