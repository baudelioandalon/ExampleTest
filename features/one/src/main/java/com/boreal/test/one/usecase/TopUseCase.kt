package com.boreal.test.one.usecase

import com.boreal.test.core.domain.In
import com.boreal.test.core.domain.Out
import com.boreal.test.core.domain.UseCase
import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.domain.PopularRepository
import com.boreal.test.one.domain.TopRepository
import com.boreal.test.one.domain.movie.MovieResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TopUseCase(private val topRepository: TopRepository) :
    UseCase<TopUseCase.Input, TopUseCase.Output> {

    class Input : In()
    inner class Output(val response: DataResponse<MovieResponseModel>) : Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return topRepository.getTopMovies()
            .flowOn(Dispatchers.IO).map {
                Output(it)
            }
    }

}