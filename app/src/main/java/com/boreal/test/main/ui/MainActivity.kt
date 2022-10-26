package com.boreal.test.main.ui

import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.base.CUBaseActivity
import com.boreal.test.R
import com.boreal.test.core.domain.TypeMovies
import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.databinding.ActivityMainBinding
import com.boreal.test.one.ui.OneViewModel
import com.google.firebase.FirebaseApp
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : CUBaseActivity<ActivityMainBinding>() {

    override fun getLayout() = R.layout.activity_main

    private val oneViewModel: OneViewModel by viewModel()
    private val viewModel: MainViewModel by viewModel()

    override fun initView() {
        CUAppInit().init(this.application, this)
        FirebaseApp.initializeApp(this)
        initElements()
    }

    override fun initObservers() {

        oneViewModel.popularMovies.observe(this) {
            it?.let {
                if (it.statusRequest == StatusRequestEnum.SUCCESS) {
                    it.successData?.let { items ->
                        viewModel.saveMovieList(TypeMovies.POPULAR.type, items.results)
                    }
                } else if (it.statusRequest == StatusRequestEnum.FAILURE) {
                    viewModel.getLocalMovie(TypeMovies.POPULAR) { listLocal ->
                        oneViewModel.retrieveData(listLocal, TypeMovies.POPULAR)
                    }
                }
            }
        }

        oneViewModel.topMovies.observe(this) {
            it?.let {
                if (it.statusRequest == StatusRequestEnum.SUCCESS) {
                    it.successData?.let { items ->
                        viewModel.saveMovieList(TypeMovies.TOP.type, items.results)
                    }
                } else if (it.statusRequest == StatusRequestEnum.FAILURE) {
                    viewModel.getLocalMovie(TypeMovies.TOP) { listLocal ->
                        oneViewModel.retrieveData(listLocal, TypeMovies.TOP)
                    }
                }
            }
        }

        oneViewModel.playingMovies.observe(this) {
            it?.let {
                if (it.statusRequest == StatusRequestEnum.SUCCESS) {
                    it.successData?.let { items ->
                        viewModel.saveMovieList(TypeMovies.PLAYING.type, items.results)
                    }
                } else if (it.statusRequest == StatusRequestEnum.FAILURE) {
                    viewModel.getLocalMovie(TypeMovies.PLAYING) { listLocal ->
                        oneViewModel.retrieveData(listLocal, TypeMovies.PLAYING)
                    }
                }
            }
        }
    }
}