package com.boreal.test.three.data.datasource.remote

import com.boreal.test.core.domain.StorageImageModel
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.three.data.datasource.GetImagesDataSource
import com.boreal.test.three.repository.ImagesDataSource
import kotlinx.coroutines.flow.Flow

class RemoteImagesDataSource : GetImagesDataSource {

    override suspend fun executeGetImages(
        idKey: String,
        collectionPath: String
    ): Flow<FirestoreGetResponse<List<StorageImageModel>>> =
        ImagesDataSource.getImages(idKey, collectionPath)

}
