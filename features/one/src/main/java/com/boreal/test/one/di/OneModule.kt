package com.boreal.test.one.di

import com.boreal.test.core.domain.UseCase
import com.boreal.test.one.data.GetPlayingDataSource
import com.boreal.test.one.data.GetPopularDataSource
import com.boreal.test.one.data.GetTopDataSource
import com.boreal.test.one.data.datasource.remote.RemotePlayingDataSource
import com.boreal.test.one.data.datasource.remote.RemotePopularDataSource
import com.boreal.test.one.data.datasource.remote.RemoteTopDataSource
import com.boreal.test.one.domain.PlayingRepository
import com.boreal.test.one.domain.PopularRepository
import com.boreal.test.one.domain.TopRepository
import com.boreal.test.one.repository.DefaultPlayingMoviesRepository
import com.boreal.test.one.repository.DefaultPopularMoviesRepository
import com.boreal.test.one.repository.DefaultTopMoviesRepository
import com.boreal.test.one.ui.OneViewModel
import com.boreal.test.one.usecase.PlayingUseCase
import com.boreal.test.one.usecase.PopularUseCase
import com.boreal.test.one.usecase.TopUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val oneModule = module {

    single<GetPopularDataSource>(named("RemotePopularDataSource")) {
        RemotePopularDataSource()
    }
    single<PopularRepository>(named("DefaultPopularMoviesRepository")) {
        DefaultPopularMoviesRepository(get(named("RemotePopularDataSource")))
    }
    single<UseCase<PopularUseCase.Input, PopularUseCase.Output>>(named("PopularUseCase")) {
        PopularUseCase(get(named("DefaultPopularMoviesRepository")))
    }

    single<GetTopDataSource>(named("RemoteTopDataSource")) {
        RemoteTopDataSource()
    }
    single<TopRepository>(named("DefaultTopMoviesRepository")) {
        DefaultTopMoviesRepository(get(named("RemoteTopDataSource")))
    }
    single<UseCase<TopUseCase.Input, TopUseCase.Output>>(named("TopUseCase")) {
        TopUseCase(get(named("DefaultTopMoviesRepository")))
    }

    single<GetPlayingDataSource>(named("RemotePlayingDataSource")) {
        RemotePlayingDataSource()
    }
    single<PlayingRepository>(named("DefaultPlayingMoviesRepository")) {
        DefaultPlayingMoviesRepository(get(named("RemotePlayingDataSource")))
    }
    single<UseCase<PlayingUseCase.Input, PlayingUseCase.Output>>(named("PlayingUseCase")) {
        PlayingUseCase(get(named("DefaultPlayingMoviesRepository")))
    }
    viewModel {
        OneViewModel(
            get(named("PopularUseCase")),
            get(named("TopUseCase")),
            get(named("PlayingUseCase"))
        )
    }

}