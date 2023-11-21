package br.com.tcc.petsus.application.service.usecase.dashboard

import br.com.tcc.petsus.application.result.ProcessResultImpl
import br.com.tcc.petsus.application.util.currentUser
import br.com.tcc.petsus.domain.model.api.news.response.NewsResponse
import br.com.tcc.petsus.domain.model.api.news.response.NewsResponse.Companion.response
import br.com.tcc.petsus.domain.model.database.user.types.ClinicUser
import br.com.tcc.petsus.domain.model.database.user.types.TownHallUser
import br.com.tcc.petsus.domain.repository.database.address.CityRepository
import br.com.tcc.petsus.domain.repository.database.clinic.ClinicRepository
import br.com.tcc.petsus.domain.repository.database.news.NewsRepository
import br.com.tcc.petsus.domain.repository.database.townhall.TownHallRepository
import br.com.tcc.petsus.domain.result.ProcessResult
import br.com.tcc.petsus.domain.services.usecase.dashboard.DashboardUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DashboardUseCaseImpl @Autowired constructor(
    @Autowired private val newsRepository: NewsRepository,
    @Autowired private val clinicRepository: ClinicRepository,
    @Autowired private val townHallRepository: TownHallRepository,
) : DashboardUseCase {
    override fun news(): ProcessResult {
        return when (val user = currentUser) {
            is ClinicUser -> {
                val clinic = clinicRepository.findByAdmUser(user.id)
                if (clinic.isEmpty)
                    return ProcessResultImpl.successful(data = arrayOf<NewsResponse>())
                ProcessResultImpl.successful(data = newsRepository.news(cityId = clinic.get().address.city.id).map { it.response() })
            }

            is TownHallUser ->  {
                val townHall = townHallRepository.findByUserId(user.id)
                if (townHall.isEmpty)
                    return ProcessResultImpl.successful(data = arrayOf<NewsResponse>())
                ProcessResultImpl.successful(data = newsRepository.news(cityId = townHall.get().city.id).map { it.response() })
            }

            else -> ProcessResultImpl.successful(data = arrayOf<NewsResponse>())
        }
    }

    override fun schedule(): ProcessResult {
        return when (val user = currentUser) {
            is ClinicUser -> {
                val clinic = clinicRepository.findByAdmUser(user.id)
                if (clinic.isEmpty)
                    return ProcessResultImpl.successful(data = arrayOf<NewsResponse>())
                ProcessResultImpl.successful(data = newsRepository.schedule(cityId = clinic.get().address.city.id).map { it.response() })
            }
            is TownHallUser ->  {
                val townHall = townHallRepository.findByUserId(user.id)
                if (townHall.isEmpty)
                    return ProcessResultImpl.successful(data = arrayOf<NewsResponse>())
                ProcessResultImpl.successful(data = newsRepository.schedule(cityId = townHall.get().city.id).map { it.response() })
            }

            else -> ProcessResultImpl.successful(data = arrayOf<NewsResponse>())
        }
    }
}