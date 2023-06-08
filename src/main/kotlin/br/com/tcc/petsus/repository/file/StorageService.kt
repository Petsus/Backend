package br.com.tcc.petsus.repository.file

import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile

class StorageService {
    private val images: MutableMap<String, ByteArray> = mutableMapOf()
    fun save(multipartFile: MultipartFile, name: String) {
        images[name] = multipartFile.bytes
    }
    fun get(name: String): Resource {
        return ByteArrayResource(images[name]!!)
    }
}