package com.ferechamitebeyli.data.repository.location.implementation

import com.ferechamitebeyli.data.mapper.location.LocationDataMapper
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.data.repository.location.abstraction.LocationRepository
import com.ferechamitebeyli.network.datasource.location.abstraction.LocationRemoteDataSource
import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.util.Resource
import com.ferechamitebeyli.network.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

class LocationRepositoryImpl @Inject constructor(
    private val remoteDataSource: LocationRemoteDataSource,
    private val locationDataMapper: LocationDataMapper,
) : LocationRepository {
    override suspend fun getBusLocations(body: GenericRequestModel<String>): Flow<Resource<List<LocationDataUiModel>>> {
        return safeApiCall(
            mapFromModel = {
                locationDataMapper.mapToUiModelList(it)
            }
        ) {
            remoteDataSource.getBusLocations(body)
        }
    }
}