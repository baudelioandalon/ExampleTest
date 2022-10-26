package com.boreal.test.main.ui

import androidx.lifecycle.viewModelScope
import com.boreal.test.core.domain.TypeMovies
import com.boreal.test.core.utils.core.CUBaseViewModel
import com.boreal.test.main.TestApplication
import com.boreal.test.one.domain.movie.MovieEntity
import com.boreal.test.one.domain.movie.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : CUBaseViewModel() {

    fun saveMovieList(type: String, items: List<MovieModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            items.forEachIndexed { index, model ->
                TestApplication.roomDatabase.movieDao().saveMovie(
                    MovieEntity(
                        id = model.id,
                        adult = model.adult,
                        backdrop_path = model.backdrop_path,
                        original_language = model.original_language,
                        overview = model.overview,
                        popularity = model.popularity,
                        poster_path = model.poster_path,
                        release_date = model.release_date,
                        title = model.title,
                        video = model.video,
                        movie_type = type,
                        vote_average = model.vote_average,
                        vote_count = model.vote_count
                    )
                )
            }
        }
    }

    fun getLocalMovie(type: TypeMovies, callbackList: (List<MovieModel>) -> Unit) {
        executeFlow {
            val list = TestApplication.roomDatabase.movieDao().getMovies(type = type.type)
            callbackList(list.map {
                MovieModel(
                    adult = it.adult,
                    backdrop_path = it.backdrop_path,
                    id = it.id,
                    original_language = it.original_language,
                    overview = it.overview,
                    original_title = it.original_title,
                    popularity = it.popularity,
                    poster_path = it.poster_path,
                    release_date = it.release_date,
                    title = it.title,
                    video = it.video,
                    vote_average = it.vote_average,
                    vote_count = it.vote_count
                )
            })
        }
    }
}