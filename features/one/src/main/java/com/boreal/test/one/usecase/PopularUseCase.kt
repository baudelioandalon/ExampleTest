package com.boreal.test.one.usecase

import com.boreal.test.core.domain.In
import com.boreal.test.core.domain.Out
import com.boreal.test.core.domain.UseCase
import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.domain.PopularRepository
import com.boreal.test.one.domain.movie.MovieResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PopularUseCase(private val popularRepository: PopularRepository) :
    UseCase<PopularUseCase.Input, PopularUseCase.Output> {

    class Input : In()
    inner class Output(val response: DataResponse<MovieResponseModel>) : Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return popularRepository.getPopularMovies()
            .flowOn(Dispatchers.IO).map {
                Output(it)
            }
    }

}