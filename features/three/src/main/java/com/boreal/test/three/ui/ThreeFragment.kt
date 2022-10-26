package com.boreal.test.three.ui

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.commonutils.utils.GAdapter
import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.core.domain.StorageImageModel
import com.boreal.test.three.R
import com.boreal.test.three.databinding.FragmentThreeBinding
import com.boreal.test.three.databinding.ImageItemBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ThreeFragment : CUBaseFragment<FragmentThreeBinding>() {

    internal val viewModel: ThreeViewModel by viewModel()

    val adapterImages = GAdapter<ImageItemBinding, StorageImageModel>(
        layoutId = R.layout.image_item,
        AsyncDifferConfig.Builder(object : DiffUtil.ItemCallback<StorageImageModel>() {
            override fun areItemsTheSame(
                oldItem: StorageImageModel,
                newItem: StorageImageModel
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: StorageImageModel,
                newItem: StorageImageModel
            ) =
                oldItem == newItem

        }).build(),
        holderCallback = { bindingElement, model, _, _, _ ->
            bindingElement.apply {
                storageImg = model.imageUrl
            }
        },
        null
    )

    override fun getLayout() = R.layout.fragment_three

    override fun initView() {
        initElements()
    }

    override fun initObservers() {
        viewModel.imagesList.observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    StatusRequestEnum.LOADING -> {

                    }
                    StatusRequestEnum.SUCCESS, StatusRequestEnum.FAILURE -> {
                        hideProgressBarCustom()
                        it.failure?.let { errorResult ->
                            showToast(errorResult)
                            return@observe
                        }
                        setImages(it.response ?: emptyList())
                    }
                    else -> {

                    }
                }
            }
        }
    }
}