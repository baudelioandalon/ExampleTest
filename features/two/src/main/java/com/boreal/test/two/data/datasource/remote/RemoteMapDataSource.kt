package com.boreal.test.two.data.datasource.remote

import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.two.data.datasource.GetMapDataSource
import com.boreal.test.two.repository.MapDataSource
import com.boreal.test.core.domain.LatLng
import kotlinx.coroutines.flow.Flow

class RemoteMapDataSource : GetMapDataSource {

    override suspend fun executeGetPlaces(
        idKey: String,
        collectionPath: String
    ): FirestoreGetResponse<List<LatLng>> = MapDataSource.getPlaces(idKey, collectionPath)

}
