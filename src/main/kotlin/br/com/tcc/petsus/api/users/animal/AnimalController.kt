package br.com.tcc.petsus.api.users.animal

import br.com.tcc.petsus.domain.model.api.animal.request.AnimalRequest
import br.com.tcc.petsus.domain.services.usecase.animal.AnimalUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/animal")
class AnimalController @Autowired constructor(
    private val useCase: AnimalUseCase
) {
    @GetMapping("/qrcode/{id}")
    fun registerQrCode(@PathVariable("id") animalId: Long) =
        useCase.registerQrCode(animalId = animalId).response()

    @DeleteMapping("qrcode/{id}")
    fun unregisterQrcOde(@PathVariable("id") qrCode: String) =
        useCase.unregisterQrCode(qrCode)

    @PutMapping("image/{id}")
    fun putImage(@PathVariable("id") id: Long, @RequestParam("file") file: MultipartFile) =
        useCase.putImage(file, id).response()

    @GetMapping("/image/{id}")
    fun getImage(@PathVariable("id") id: Long) =
        useCase.getImage(id).response()

    @GetMapping("/tag/{tagId}")
    fun getAnimalForTag(@PathVariable("tagId") tagId: String, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.getAnimalForTagId(tagId, uriComponentsBuilder).response()

    @GetMapping
    fun all(uriComponentsBuilder: UriComponentsBuilder) = useCase.list(uriComponentsBuilder).response()

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.find(id, uriComponentsBuilder).response()

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) =
        useCase.delete(id)

    @PostMapping
    fun create(@RequestBody @Valid body: AnimalRequest, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.create(body, uriComponentsBuilder).response()

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody @Valid body: AnimalRequest) =
        useCase.update(id, body).response()

    @PostMapping("/found")
    fun notifyAnimalFound(
        @RequestParam(name = "lat") lat: Double,
        @RequestParam(name = "lng") lng: Double,
        @RequestParam(name = "animalId") animalId: Long,
        uriComponentsBuilder: UriComponentsBuilder
    ) = useCase.notifyAnimalFounded(lat, lng, animalId, uriComponentsBuilder).response()
}