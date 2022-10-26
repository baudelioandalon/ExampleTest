package com.boreal.test.main.di

import com.boreal.test.main.ui.MainViewModel
import com.boreal.test.one.ui.OneViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val activityModule = module {

    viewModel {
        OneViewModel(
            get(named("PopularUseCase")),
            get(named("TopUseCase")),
            get(named("PlayingUseCase"))
        )
    }

    viewModel {
        MainViewModel()
    }
}