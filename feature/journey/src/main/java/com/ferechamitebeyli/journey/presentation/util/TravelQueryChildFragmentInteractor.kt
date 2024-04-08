package com.ferechamitebeyli.journey.presentation.util

import com.ferechamitebeyli.journey.presentation.argument.JourneyNavArgument

interface TravelQueryChildFragmentInteractor {
    fun onBusQueryClick(args: JourneyNavArgument)

    fun onFlightQueryClick(args: JourneyNavArgument)
}