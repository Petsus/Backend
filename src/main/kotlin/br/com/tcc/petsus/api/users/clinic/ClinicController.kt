package br.com.tcc.petsus.api.users.clinic

import br.com.tcc.petsus.domain.services.usecase.clinic.ClinicUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

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

}