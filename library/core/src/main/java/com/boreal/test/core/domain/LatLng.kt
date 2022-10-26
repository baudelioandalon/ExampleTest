package com.boreal.test.core.domain

import com.google.firebase.Timestamp

data class LatLng(
    val latitude: String = "",
    val longitude: String = "",
    val creationDate: Timestamp = Timestamp(0L, 0)
)
