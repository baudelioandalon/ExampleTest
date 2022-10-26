package com.boreal.test.one.ui

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.utils.GAdapter
import com.boreal.test.core.BASE_IMG
import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.one.R
import com.boreal.test.one.databinding.FragmentOneBinding
import com.boreal.test.one.databinding.MovieItemBinding
import com.boreal.test.one.domain.movie.MovieModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class OneFragment : CUBaseFragment<FragmentOneBinding>() {

    private val viewModel: OneViewModel by sharedViewModel()

    val adapterPopular = GAdapter<MovieItemBinding, MovieModel>(
        layoutId = R.layout.movie_item,
        AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(
                oldItem: MovieModel,
                newItem: MovieModel
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: MovieModel,
                newItem: MovieModel
            ) =
                oldItem == newItem

        }).build(),
        holderCallback = { bindingElement, model, _, _, _ ->
            bindingElement.apply {
                movieImg = "$BASE_IMG${model.poster_path}"
            }
        },
        null
    )
    val adapterTopRated = GAdapter<MovieItemBinding, MovieModel>(
        layoutId = R.layout.movie_item,
        AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(
                oldItem: MovieModel,
                newItem: MovieModel
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: MovieModel,
                newItem: MovieModel
            ) =
                oldItem == newItem

        }).build(),
        holderCallback = { bindingElement, model, _, _, _ ->
            bindingElement.apply {
                movieImg = "$BASE_IMG${model.poster_path}"
            }
        },
        null
    )
    val adapterPlaying = GAdapter<MovieItemBinding, MovieModel>(
        layoutId = R.layout.movie_item,
        AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(
                oldItem: MovieModel,
                newItem: MovieModel
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: MovieModel,
                newItem: MovieModel
            ) = oldItem == newItem

        }).build(),
        holderCallback = { bindingElement, model, _, _, _ ->
            bindingElement.apply {
                movieImg = "$BASE_IMG${model.poster_path}"
            }
        },
        null
    )

    override fun getLayout() = R.layout.fragment_one

    override fun initView() {
        initElements()
        viewModel.requestPopular()
        viewModel.requestTop()
        viewModel.requestPlaying()
    }

    override fun initObservers() {
        viewModel.popularMovies.observe(viewLifecycleOwner) {
            it?.let {
                when (it.statusRequest) {
                    StatusRequestEnum.LOADING -> {
                        showProgress()
                    }
                    StatusRequestEnum.SUCCESS, StatusRequestEnum.SUCCESS_LOCAL -> {
                        it.successData?.let { items ->
                            setPopularResults(items.results)
                        }
                        hideProgressBarCustom()
                    }

                    StatusRequestEnum.FAILURE -> {
                        it.errorData
                        hideProgressBarCustom()
                        showToast(it.errorData ?: "")
                    }
                    else -> {

                    }
                }
            }
        }

        viewModel.topMovies.observe(viewLifecycleOwner) {
            it?.let {
                when (it.statusRequest) {
                    StatusRequestEnum.LOADING -> {
                    }
                    StatusRequestEnum.SUCCESS, StatusRequestEnum.SUCCESS_LOCAL  -> {
                        it.successData?.let { items ->
                            setTopResults(items.results)
                        }
                        hideProgressBarCustom()
                    }
                    StatusRequestEnum.FAILURE -> {
                        it.errorData
                        hideProgressBarCustom()
                        showToast(it.errorData ?: "")
                    }
                    else -> {

                    }
                }
            }
        }

        viewModel.playingMovies.observe(viewLifecycleOwner) {
            it?.let {
                when (it.statusRequest) {
                    StatusRequestEnum.LOADING -> {
                    }
                    StatusRequestEnum.SUCCESS, StatusRequestEnum.SUCCESS_LOCAL  -> {
                        it.successData?.let { items ->
                            setPlayingResults(items.results)
                        }
                        hideProgressBarCustom()
                    }
                    StatusRequestEnum.FAILURE -> {
                        it.errorData
                        hideProgressBarCustom()
                        showToast(it.errorData ?: "")
                    }
                    else -> {

                    }
                }
            }
        }
    }
}