package com.boreal.test.two.di

import com.boreal.test.core.domain.UseCase
import com.boreal.test.two.data.datasource.GetMapDataSource
import com.boreal.test.two.data.datasource.remote.RemoteMapDataSource
import com.boreal.test.two.domain.MapRepository
import com.boreal.test.two.repository.DefaultMapRepository
import com.boreal.test.two.ui.TwoViewModel
import com.boreal.test.two.usecase.MapUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val twoModule = module {

    single<GetMapDataSource>(named("RemoteMapDataSource")) {
        RemoteMapDataSource()
    }

    single<MapRepository>(named("DefaultMapRepository")) {
        DefaultMapRepository(
            get(named("RemoteMapDataSource"))
        )
    }

    single<UseCase<MapUseCase.Input, MapUseCase.Output>>(named("MapUseCase")) {
        MapUseCase(get(named("DefaultMapRepository")))
    }

    viewModel {
        TwoViewModel(
            get(named("MapUseCase"))
        )
    }
}