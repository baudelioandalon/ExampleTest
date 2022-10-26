package com.boreal.test.three.di

import com.boreal.test.core.domain.UseCase
import com.boreal.test.three.data.datasource.GetImagesDataSource
import com.boreal.test.three.data.datasource.remote.RemoteImagesDataSource
import com.boreal.test.three.domain.ImagesRepository
import com.boreal.test.three.repository.DefaultImagesRepository
import com.boreal.test.three.ui.ThreeViewModel
import com.boreal.test.three.usecase.ImagesUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val threeModule = module {
    single<GetImagesDataSource>(named("RemoteImagesDataSource")) {
        RemoteImagesDataSource()
    }

    single<ImagesRepository>(named("DefaultImagesRepository")) {
        DefaultImagesRepository(
            get(named("RemoteImagesDataSource"))
        )
    }

    single<UseCase<ImagesUseCase.Input, ImagesUseCase.Output>>(named("ImagesUseCase")) {
        ImagesUseCase(get(named("DefaultImagesRepository")))
    }

    viewModel {
        ThreeViewModel(
            get(named("ImagesUseCase"))
        )
    }
}