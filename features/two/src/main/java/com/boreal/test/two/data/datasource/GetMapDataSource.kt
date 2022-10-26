package com.boreal.test.two.data.datasource

import com.boreal.test.core.domain.LatLng
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse

interface GetMapDataSource {
    suspend fun executeGetPlaces(
        idKey: String = "",
        collectionPath: String = ""
    ): FirestoreGetResponse<List<LatLng>>
}