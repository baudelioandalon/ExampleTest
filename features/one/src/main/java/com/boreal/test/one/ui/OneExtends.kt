package com.boreal.test.one.ui

import com.boreal.test.one.domain.movie.MovieModel

internal fun OneFragment.initElements() {
    initAdapters()
}

internal fun OneFragment.initAdapters() {
    binding.apply {
        rvPopular.adapter = adapterPopular
        rvTop.adapter = adapterTopRated
        rvPlaying.adapter = adapterPlaying
    }
}

internal fun OneFragment.setPopularResults(result: List<MovieModel>) {
    adapterPopular.submitList(result)
}

internal fun OneFragment.setTopResults(result: List<MovieModel>) {
    adapterTopRated.submitList(result)
}

internal fun OneFragment.setPlayingResults(result: List<MovieModel>) {
    adapterPlaying.submitList(result)
}