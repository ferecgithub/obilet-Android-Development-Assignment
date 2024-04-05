package com.ferechamitebeyli.data.repository.location.abstraction

import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.network.dto.common.request.GenericRequestModel
import com.ferechamitebeyli.network.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

interface LocationRepository {

    suspend fun getBusLocations(body: GenericRequestModel<String>): Flow<Resource<List<LocationDataUiModel>>>
}