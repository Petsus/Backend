package br.com.tcc.petsus.application.service.usecase.news

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.repository.news.NewsRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.news.NewsUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class NewsUseCaseImpl @Autowired constructor(
    @Autowired private val newsRepository: NewsRepository
) : NewsUseCase {
    override fun list(): ProcessResult =
        ProcessResultImpl.successful(data = newsRepository.list(userId = currentUser().id))
}