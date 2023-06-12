package br.com.tcc.petsus.domain.result

import org.springframework.http.ResponseEntity
import java.net.URI

interface ProcessResult {
    fun location(location: URI): ProcessResult
    fun response(): ResponseEntity<*>
}