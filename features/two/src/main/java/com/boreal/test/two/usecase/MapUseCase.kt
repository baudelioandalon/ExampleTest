package com.boreal.test.two.usecase

import com.boreal.test.core.domain.In
import com.boreal.test.core.domain.Out
import com.boreal.test.core.domain.UseCase
import com.boreal.test.core.utils.core.firestore.FirestoreGetResponse
import com.boreal.test.two.domain.MapRepository
import com.boreal.test.core.domain.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MapUseCase(private val mapRepository: MapRepository) :
    UseCase<MapUseCase.Input, MapUseCase.Output> {

    class Input(
        val idKey: String,
        val collectionPath: String
    ) : In()

    inner class Output(val response: FirestoreGetResponse<List<LatLng>>) :
        Out()

    override suspend fun execute(input: Input): Flow<Output> {
        return mapRepository.executeGetPlaces(input.idKey, input.collectionPath)
            .flowOn(Dispatchers.IO).map {
                Output(it)
            }
    }

}