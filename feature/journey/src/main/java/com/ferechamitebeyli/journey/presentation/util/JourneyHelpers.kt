package com.ferechamitebeyli.journey.presentation.util

import com.ferechamitebeyli.caching.model.LastQueryUiModel

object JourneyHelpers {
    fun validateCachedQuery(query: LastQueryUiModel?): Boolean =
        ((query?.originName.isNullOrBlank() && query?.originId == null) || (query?.destinationName.isNullOrBlank() && query?.destinationId == null) || (query?.departureDateForService.isNullOrBlank() && query?.departureDateForUi.isNullOrBlank())).not()
}