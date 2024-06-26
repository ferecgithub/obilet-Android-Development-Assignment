package com.ferechamitebeyli.data.mapper.location

import com.ferechamitebeyli.data.mapper.base.BaseMapper
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.network.dto.location.getbuslocations.response.Data
import javax.inject.Inject

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

class LocationDataMapper @Inject constructor() : BaseMapper<Data, LocationDataUiModel> {
    override fun mapToUiModel(model: Data): LocationDataUiModel {
        return LocationDataUiModel(
            id = model.id,
            parentId = model.parentId,
            type = model.type,
            name = model.name,
            cityId = model.cityId,
            cityName = model.cityName,
        )
    }

}