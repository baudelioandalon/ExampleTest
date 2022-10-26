package com.boreal.test.one.data.datasource.remote

import com.boreal.test.core.utils.core.DataResponse
import com.boreal.test.one.data.GetPlayingDataSource
import com.boreal.test.one.data.GetPopularDataSource
import com.boreal.test.one.domain.movie.MovieResponseModel

class RemotePlayingDataSource : GetPlayingDataSource {
    override suspend fun getPlayingMovies(): DataResponse<MovieResponseModel> {
        return PlayingDataSource.getData()
    }
}
