package br.com.tcc.petsus.controller.address

import br.com.tcc.petsus.error.model.ErrorResponse
import br.com.tcc.petsus.model.address.address.*
import br.com.tcc.petsus.model.address.city.City
import br.com.tcc.petsus.model.address.city.CityResponse
import br.com.tcc.petsus.model.address.city.toResponse
import br.com.tcc.petsus.model.address.state.State
import br.com.tcc.petsus.model.address.state.StateResponse
import br.com.tcc.petsus.model.address.state.toResponse
import br.com.tcc.petsus.model.base.DataResponse
import br.com.tcc.petsus.model.user.base.User
import br.com.tcc.petsus.model.user.UserRole
import br.com.tcc.petsus.repository.AddressRepository
import br.com.tcc.petsus.repository.CityRepository
import br.com.tcc.petsus.repository.StateRepository
import br.com.tcc.petsus.util.cast
import br.com.tcc.petsus.util.getOrThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.stream.Collectors
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping(value = ["/address"])
class AddressController {

    @Autowired
    private lateinit var repositoryState: StateRepository

    @Autowired
    private lateinit var repositoryCity: CityRepository

    @Autowired
    private lateinit var repositoryAddress: AddressRepository

    @Cacheable(value = ["state"])
    @GetMapping(value = ["/state"])
    fun state(): ResponseEntity<DataResponse<List<StateResponse>>> {
        return ResponseEntity.ok(
            DataResponse(
                data = repositoryState.findAll().stream().map(State::toResponse).collect(Collectors.toList())
            )
        )
    }

    @Cacheable(value = ["city"])
    @GetMapping(value = ["/city"])
    fun city(): ResponseEntity<DataResponse<List<CityResponse>>> {
        return ResponseEntity.ok(
            DataResponse(
                data = repositoryCity.findAll().stream().map(City::toResponse).collect(Collectors.toList())
            )
        )
    }

    @PostMapping
    @Transactional
    fun create(@RequestBody @Valid address: AddressRequest, uriBuilder: UriComponentsBuilder): ResponseEntity<*> {
        val city = repositoryCity.findById(address.cityId.getOrThrow())
        if (city.isEmpty)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "City not exists", data = address.cityId))

        val authentication = SecurityContextHolder.getContext().authentication
        val id = authentication.principal.cast<User>().id

        val newAddress = when (authentication.authorities.first().authority) {
            UserRole.USER.authority.first().authority -> address.toAddress(city = city.get(), userId = id)
            UserRole.CLINIC.authority.first().authority -> address.toAddress(city = city.get(), clinicId = id)
            else -> throw NotImplementedError()
        }

        val userAddress = repositoryAddress.save(newAddress)

        return ResponseEntity.created(
            uriBuilder.path("/user/{id}").buildAndExpand(userAddress.id).toUri()
        ).body(
            DataResponse(data = userAddress.toResponse())
        )
    }

    @GetMapping
    fun list(): ResponseEntity<DataResponse<List<AddressResponse>>> {
        val authentication = SecurityContextHolder.getContext().authentication
        val id = authentication.principal.cast<User>().id

        val address = when (authentication.authorities.first().authority) {
            UserRole.USER.authority.first().authority -> repositoryAddress.findByUserId(id)
            UserRole.CLINIC.authority.first().authority -> repositoryAddress.findByClinicId(id)
            else -> throw NotImplementedError()
        }

        return ResponseEntity.ok(
            DataResponse(data = address.stream().map(Address::toResponse).collect(Collectors.toList()))
        )
    }

    @GetMapping("/{id}")
    fun find(@PathVariable("id") id: Long): ResponseEntity<*> {
        val address = repositoryAddress.findById(id)

        val authentication = SecurityContextHolder.getContext().authentication
        val idUser = authentication.principal.cast<User>().id

        if (address.isEmpty)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "Address not found", data = id))
        if (address.get().clinic?.id != idUser && address.get().userId != idUser)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "Address not found", data = id))
        return ResponseEntity.ok(
            DataResponse(data = address.get())
        )
    }

    @PutMapping("/{id}")
    @Transactional
    fun update(@PathVariable("id") id: Long, @RequestBody addressRequest: AddressUpdate): ResponseEntity<*> {
        val city = repositoryCity.findById(addressRequest.cityId.getOrThrow())
        if (city.isEmpty)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "City not exists", data = addressRequest.cityId))


        val authentication = SecurityContextHolder.getContext().authentication
        val idUser = authentication.principal.cast<User>().id

        val address = repositoryAddress.findById(id).orElse(null) ?: return ResponseEntity.badRequest().body(ErrorResponse(message = "Address not found", data = id))

        if (address.clinic?.id != idUser && address.userId != idUser)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "Address not found", data = id))

        addressRequest.copyTo(address, city.get())
        val save = repositoryAddress.save(address)

        return ResponseEntity.ok(
            DataResponse(data = save)
        )
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<*> {
        val authentication = SecurityContextHolder.getContext().authentication
        val idUser = authentication.principal.cast<User>().id

        val address = repositoryAddress.findById(id).orElse(null) ?: return ResponseEntity.badRequest().body(ErrorResponse(message = "Address not found", data = id))

        if (address.clinic?.id != idUser && address.userId != idUser)
            return ResponseEntity.badRequest().body(ErrorResponse(message = "Address not found", data = id))

        repositoryAddress.deleteById(id)

        return ResponseEntity.noContent().build<Any>()
    }

}