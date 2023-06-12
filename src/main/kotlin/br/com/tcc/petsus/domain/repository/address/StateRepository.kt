package br.com.tcc.petsus.domain.repository.address

import br.com.tcc.petsus.domain.model.address.State
import org.springframework.data.jpa.repository.JpaRepository

interface StateRepository : JpaRepository<State, Long>