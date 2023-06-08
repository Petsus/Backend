package br.com.tcc.petsus.model.address.state

data class StateResponse(
    val id: Long,
    val name: String,
    val initials: String,
)

fun State.toResponse(): StateResponse =
    StateResponse(id, name, initials)