package br.com.tcc.petsus.application.result

import br.com.tcc.petsus.domain.model.api.error.response.ErrorResponse
import br.com.tcc.petsus.domain.model.database.base.DataResponse
import br.com.tcc.petsus.domain.result.ProcessResult
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.net.URI

class ProcessResultImpl private constructor(
    private val status: HttpStatus,
    private val response: DataResponse<*>? = null,
    private val error: ErrorResponse<*>? = null,
    private val resource: Resource? = null,
    private val mediaType: MediaType? = null
)  : ProcessResult {
    private var location: URI? = null

    companion object {
        @JvmStatic
        fun error(error: ErrorResponse<*>?, status: HttpStatus = HttpStatus.BAD_REQUEST): ProcessResult {
            return ProcessResultImpl(
                error = error,
                status = status
            )
        }

        @JvmStatic
        fun successful(data: Any?, status: HttpStatus = HttpStatus.OK): ProcessResult {
            return ProcessResultImpl(
                status = status,
                response = DataResponse(data = data)
            )
        }

        @JvmStatic
        fun resource(resource: Resource, mediaType: MediaType, status: HttpStatus = HttpStatus.OK): ProcessResult {
            return ProcessResultImpl(
                status = status,
                resource = resource,
                mediaType = mediaType
            )
        }
    }

    override fun location(location: URI): ProcessResult {
        this.location = location
        return this
    }

    override fun response(): ResponseEntity<*> {
        if (resource != null)
            return ResponseEntity
                .status(status)
                .contentType(mediaType ?: MediaType.ALL)
                .contentLength(resource.contentLength())
                .body(resource)
        return ResponseEntity
            .status(status)
            .apply {
                location?.run(this::location)
            }
            .body(response ?: error)
    }

}