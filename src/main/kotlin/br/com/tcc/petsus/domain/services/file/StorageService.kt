package br.com.tcc.petsus.domain.services.file

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

interface StorageService {
    fun save(multipartFile: MultipartFile, name: String)
    fun get(name: String): Resource
}