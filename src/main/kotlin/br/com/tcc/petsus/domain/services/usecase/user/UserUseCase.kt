package br.com.tcc.petsus.domain.services.usecase.user

import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.web.multipart.MultipartFile

interface UserUseCase {
    fun get(): ProcessResult
    fun get(id: Long): ProcessResult
    fun remove(id: Long): ProcessResult
    fun putImage(file: MultipartFile): ProcessResult
    fun getImage(): ProcessResult
}