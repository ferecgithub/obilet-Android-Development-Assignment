package com.ferechamitebeyli.journey.presentation.argument

import android.os.Parcelable
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class JourneyNavArgument(
    val locationModelList: List<LocationDataUiModel>? = null, // Passed from BusQueryFragment to QueryFragment
    val isOrigin: Boolean? = null, // Determines which view the query fragment inflated from.
    val originLocationModel: LocationDataUiModel? = null, // Passed from QueryFragment back to BusQueryFragment
    val destinationLocationModel: LocationDataUiModel? = null, // Passed from QueryFragment back to BusQueryFragment
    val departureDate: String? = null, // Passed from BusQueryFragment back to BusJourneyIndexFragment
    val departureDateForUi: String? = null, // Passed from BusQueryFragment back to BusJourneyIndexFragment
    val lastSelectedTabIndex: Int? = null
): Parcelable
