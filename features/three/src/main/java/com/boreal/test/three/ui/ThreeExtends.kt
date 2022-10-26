package com.boreal.test.three.ui

import android.content.Intent
import com.boreal.commonutils.extensions.onClick
import com.boreal.test.core.component.bottomsheet.BottomSheetOptionsImageFragment
import com.boreal.test.core.domain.StorageImageModel
import com.boreal.test.core.utils.UploadImageService

internal fun ThreeFragment.initElements() {
    binding.apply {
        recyclerViewImages.adapter = adapterImages
        btnAddGallery.onClick {
            BottomSheetOptionsImageFragment { uriReceived ->
                requireActivity().startService(
                    Intent(context, UploadImageService::class.java).apply {
                        action = UploadImageService.ACTION_START_OR_RESUME_SERVICE
                        putExtra("mainImage", uriReceived.toString())
                    })
            }.show(
                requireActivity().supportFragmentManager,
                "imageSelector"
            )
        }

        viewModel.getImagesUrl()
    }
}

internal fun ThreeFragment.setImages(list: List<StorageImageModel>) {
    adapterImages.submitList(list)
}