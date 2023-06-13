package br.com.tcc.petsus.api.animal

import br.com.tcc.petsus.domain.services.usecase.animal.AnimalUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.util.UriComponentsBuilder

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
        useCase.getAnimalForTagId(tagId, uriComponentsBuilder)
    @GetMapping
    fun all() = useCase.list().response()
    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long, uriComponentsBuilder: UriComponentsBuilder) =
        useCase.find(id, uriComponentsBuilder).response()
    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) =
        useCase.delete(id)
}