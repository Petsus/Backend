package br.com.tcc.petsus.domain.repository.qrcode

import br.com.tcc.petsus.domain.model.database.qrcode.QrCode
import org.springframework.data.jpa.repository.JpaRepository

interface QrCodeRepository : JpaRepository<QrCode, Long> {
    fun deleteByQrCode(qrCode: String)
}