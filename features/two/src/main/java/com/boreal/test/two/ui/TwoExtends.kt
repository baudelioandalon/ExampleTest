package com.boreal.test.two.ui

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import com.boreal.commonutils.extensions.onClick
import com.boreal.test.core.domain.LatLng
import com.boreal.test.core.utils.ServiceLocation
import com.boreal.test.core.utils.ServiceLocation.Companion.START_SERVICE
import com.boreal.test.core.utils.ServiceLocation.Companion.STOP_SERVICE
import com.boreal.test.core.utils.SharedPreferenceTest
import com.boreal.test.core.utils.core.firestore.getFormatWithDay
import com.boreal.test.two.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import com.google.android.gms.maps.model.LatLng as googleLatLng

internal fun TwoFragment.initElements() {
    binding.apply {
        initMap()
    }
}

internal fun TwoFragment.initListener() {
    binding.btnLocation.onClick {
        getPermissionsLocation {
            pref.isRunningService = !pref.isRunningService

            val intent = Intent(
                requireContext(),
                ServiceLocation::class.java
            )
            if (pref.isRunningService) {
                requireActivity().startService(
                    intent.apply {
                        action = START_SERVICE
                    })
                binding.tvStatusLocation.text = getString(R.string.stop_location)
            } else {
                requireActivity().stopService(
                    intent.apply {
                        action = STOP_SERVICE
                    })
                binding.tvStatusLocation.text = getString(R.string.start_location)
            }
        }
    }
}

internal fun TwoFragment.initMap() {
    supportMapFragment = childFragmentManager.findFragmentById(
        R.id.map_fragment
    ) as? SupportMapFragment
    supportMapFragment?.getMapAsync { googleMap ->
        this.googleMap = googleMap
        viewModel.getPlaces()
        pref = SharedPreferenceTest(requireContext())
        if (pref.isRunningService) {
            binding.tvStatusLocation.text = getString(R.string.stop_location)
        }
        initListener()
    }
}

fun TwoFragment.addMarkers(places: List<LatLng>) {

    places.forEachIndexed { index, place ->
        val marker = MarkerOptions()
            .position(
                googleLatLng(
                    place.latitude.toFloat().toDouble(),
                    place.longitude.toFloat().toDouble()
                )
            )
            .title(
                getCompleteAddressName(
                    googleLatLng(
                        place.latitude.toFloat().toDouble(),
                        place.longitude.toFloat().toDouble()
                    )
                )
            ).snippet(place.creationDate.getFormatWithDay())
        googleMap.addMarker(marker)
        if (index == 0) {
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    googleLatLng(
                        place.latitude.toFloat().toDouble(),
                        place.longitude.toFloat().toDouble()
                    ), 16.0f
                )
            )
        }
    }
}


fun TwoFragment.getCompleteAddressName(latLng: googleLatLng): String {
    var strAdd = "Default"
    activity?.let { activity ->
        val geocoder = Geocoder(activity, Locale.getDefault())
        val addresses: ArrayList<Address>
        try {
            addresses =
                geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1) as ArrayList<Address>
            val address: String = addresses[0].getAddressLine(0)

            strAdd = address
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return strAdd
}

