package com.boreal.test.one.repository

import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.data.GetPlayingDataSource
import com.boreal.test.one.data.GetPopularDataSource
import com.boreal.test.one.domain.PlayingRepository
import com.boreal.test.one.domain.PopularRepository
import com.boreal.test.one.domain.movie.MovieResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultPlayingMoviesRepository(
    private val getPlayingDataSource: GetPlayingDataSource
) : PlayingRepository {
    override suspend fun getPlayingMovies(): Flow<DataResponse<MovieResponseModel>> = flow {
        emit(getPlayingDataSource.getPlayingMovies())
    }
}