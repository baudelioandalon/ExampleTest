package com.boreal.test.two.repository

import com.boreal.test.core.domain.LatLng
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.two.data.datasource.GetMapDataSource
import com.boreal.test.two.domain.MapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultMapRepository(
    private val getMapDataSource: GetMapDataSource
) : MapRepository {

    override suspend fun executeGetPlaces(
        idKey: String,
        collectionPath: String
    ): Flow<FirestoreGetResponse<List<LatLng>>> =
        flow {
            emit(getMapDataSource.executeGetPlaces(idKey, collectionPath))
        }
}