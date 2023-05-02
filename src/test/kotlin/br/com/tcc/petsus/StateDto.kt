package br.com.tcc.petsus

import java.io.Serializable
import java.util.*

/**
 * A DTO for the {@link br.com.tcc.petsus.model.address.state.State} entity
 */
data class StateDto(val id: Long? = null, val createdAt: Date? = null, val updatedAt: Date? = null, val name: String? = null, val initials: String? = null, val ibgeId: Int? = null) : Serializable