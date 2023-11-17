package br.com.tcc.petsus.application.service.file

import br.com.tcc.petsus.domain.services.file.StorageService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class StorageServiceImpl : StorageService {
    private val images: MutableMap<String, ByteArray> = mutableMapOf()

    override fun get(name: String): Resource = ByteArrayResource(images[name]!!)
    override fun save(multipartFile: MultipartFile, name: String) {
        images[name] = multipartFile.bytes
    }

    override fun has(name: String): Boolean = images.containsKey(name)
}