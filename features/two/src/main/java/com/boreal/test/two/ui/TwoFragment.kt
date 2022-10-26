package com.boreal.test.two.ui

import com.boreal.commonutils.base.CUBaseFragment
import com.boreal.commonutils.extensions.showToast
import com.boreal.test.core.utils.SharedPreferenceTest
import com.boreal.test.core.utils.core.StatusRequestEnum
import com.boreal.test.two.R
import com.boreal.test.two.databinding.FragmentTwoBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TwoFragment : CUBaseFragment<FragmentTwoBinding>() {

    internal var supportMapFragment: SupportMapFragment? = null
    lateinit var googleMap: GoogleMap
    internal val viewModel: TwoViewModel by viewModel()
    lateinit var pref: SharedPreferenceTest

    override fun getLayout() = R.layout.fragment_two

    override fun initView() {
        initElements()
    }

    override fun initObservers() {
        viewModel.placesList.observe(viewLifecycleOwner) {
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
                        addMarkers(it.response ?: emptyList())
                    }
                    else -> {

                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetData()
    }

}