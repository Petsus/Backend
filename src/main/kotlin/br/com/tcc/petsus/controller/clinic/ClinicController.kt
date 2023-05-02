package br.com.tcc.petsus.controller.clinic

import br.com.tcc.petsus.model.base.DataResponse
import br.com.tcc.petsus.model.clinic.ClinicAddress
import br.com.tcc.petsus.model.clinic.ClinicDetails
import br.com.tcc.petsus.model.clinic.toDetails
import br.com.tcc.petsus.repository.ClinicCompleteRepository
import br.com.tcc.petsus.repository.ClinicRepository
import br.com.tcc.petsus.util.toMiller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/clinic"])
class ClinicController {

    @Autowired
    private lateinit var repository: ClinicRepository

    @Autowired
    private lateinit var repositoryComplete: ClinicCompleteRepository

    @GetMapping(value = ["/near"])
    fun all(@RequestParam(name = "lat") lat: Double, @RequestParam(name = "lng") lng: Double, @RequestParam("distance") distance: Double): ResponseEntity<List<ClinicAddress>> {
        return ResponseEntity.ok(repository.findNear(lat, lng, distance.times(1000).toMiller()))
    }

    @GetMapping(value = ["/{id}"])
    fun find(@PathVariable(value = "id") id: Long): ResponseEntity<DataResponse<ClinicDetails>> {
        val clinic = repositoryComplete.findById(id).orElseGet(null) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(DataResponse(data = clinic.toDetails()))
    }

}