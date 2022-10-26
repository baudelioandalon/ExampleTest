package com.boreal.test.core.component.bottomsheet

import com.boreal.commonutils.extensions.onClick

fun BottomSheetOptionsImageFragment.initElements() {
    mBinding.apply {
        roundableGalery.onClick { getPermissionsStorage() }
        roundableCamera.onClick { getPermissionsCamera() }
    }
}