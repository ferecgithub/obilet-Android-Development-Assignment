package com.ferechamitebeyli.journey.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ferechamitebeyli.caching.model.LastQueryUiModel
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.domain.usecase.busquery.GetBusLocationsUseCase
import com.ferechamitebeyli.journey.domain.usecase.busquery.ValidateBusQueryInfoUseCase
import com.ferechamitebeyli.journey.domain.usecase.common.CacheLastQueriedDepartureDateUseCase
import com.ferechamitebeyli.journey.domain.usecase.common.CacheLastQueriedDestinationUseCase
import com.ferechamitebeyli.journey.domain.usecase.common.CacheLastQueriedOriginUseCase
import com.ferechamitebeyli.journey.domain.usecase.common.GetCachedLastQueryUseCase
import com.ferechamitebeyli.journey.domain.usecase.flightquery.ValidateFlightQueryInfoUseCase
import com.ferechamitebeyli.journey.presentation.argument.JourneyNavArgument
import com.ferechamitebeyli.journey.presentation.state.BusQueryQuickSelectionButtonState
import com.ferechamitebeyli.journey.presentation.state.BusQueryUiState
import com.ferechamitebeyli.journey.presentation.state.FlightQueryUiState
import com.ferechamitebeyli.journey.presentation.state.JourneyResponseState
import com.ferechamitebeyli.journey.presentation.util.JourneyHelpers.validateCachedQuery
import com.ferechamitebeyli.network.util.Resource
import com.ferechamitebeyli.ui.util.UiHelpers.getCurrentDateTime
import com.ferechamitebeyli.ui.util.UiHelpers.getFormattedDateForArrivalDateAccordingToDepartureDate
import com.ferechamitebeyli.ui.util.UiHelpers.getFormattedDateForQuickSelection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TravelQueryViewModel @Inject constructor(
    private val getBusLocationsUseCase: GetBusLocationsUseCase,
    private val getCachedLastQueryUseCase: GetCachedLastQueryUseCase,
    private val cacheLastQueriedOriginUseCase: CacheLastQueriedOriginUseCase,
    private val cacheLastQueriedDestinationUseCase: CacheLastQueriedDestinationUseCase,
    private val cacheLastQueriedDepartureDateUseCase: CacheLastQueriedDepartureDateUseCase,
    private val validateBusQueryInfoUseCase: ValidateBusQueryInfoUseCase,
    private val validateFlightQueryInfoUseCase: ValidateFlightQueryInfoUseCase,
) : ViewModel() {

    private val currentDate = getCurrentDateTime()

    var departureDateForService: String = ""
    var departureDateForUi: String = ""
    var arrivalDateForService: String = ""
    var arrivalDateForUi: String = ""

    var arguments: JourneyNavArgument? = null

    var currentOrigin: LocationDataUiModel? = null
    var currentDestination: LocationDataUiModel? = null

    private val _getBusLocationsStateFlow: MutableStateFlow<JourneyResponseState<List<LocationDataUiModel>>> =
        MutableStateFlow(
            JourneyResponseState.Idle()
        )
    val getBusLocationsStateFlow = _getBusLocationsStateFlow.asStateFlow()

    private val _getCachedLastQueryStateFlow: MutableStateFlow<LastQueryUiModel?> =
        MutableStateFlow(null)
    private val getCachedLastQueryStateFlow = _getCachedLastQueryStateFlow.asStateFlow()

    private val _busQueryFragmentUiStateFlow: MutableStateFlow<JourneyResponseState<BusQueryUiState?>> =
        MutableStateFlow(JourneyResponseState.Idle())
    val busQueryFragmentUiStateFlow = _busQueryFragmentUiStateFlow.asStateFlow()

    private val _flightQueryFragmentUiStateFlow: MutableStateFlow<JourneyResponseState<FlightQueryUiState?>> =
        MutableStateFlow(JourneyResponseState.Idle())
    val flightQueryFragmentUiStateFlow = _flightQueryFragmentUiStateFlow.asStateFlow()


    fun getBusLocations(data: String?, date: String = currentDate) =
        viewModelScope.launch {
            getBusLocationsUseCase(data, date).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        response.error?.let { uiText ->
                            _getBusLocationsStateFlow.update { JourneyResponseState.Error(text = uiText) }
                        }
                    }

                    is Resource.Loading -> {
                        _getBusLocationsStateFlow.update { JourneyResponseState.Loading() }
                    }

                    is Resource.Success -> {
                        response.data?.let { data ->
                            _getBusLocationsStateFlow.update { JourneyResponseState.Success(data) }
                        }
                    }
                }
            }
        }

    fun validateBusQueryInformation(
        departureModel: LocationDataUiModel? = currentOrigin,
        destinationModel: LocationDataUiModel? = currentDestination,
    ): Boolean {
        Log.d("TQVN", "departureModel is null = ${departureModel == null}")
        Log.d("TQVN", "destinationModel is null = ${destinationModel == null}")
        return if (departureModel == null || destinationModel == null) {
            false
        } else {
            validateBusQueryInfoUseCase.invoke(
                departureModel, destinationModel
            )
        }
    }

    fun validateFlightQueryInformation(
        departureModel: LocationDataUiModel? = currentOrigin,
        destinationModel: LocationDataUiModel? = currentDestination,
        departureDate: String = departureDateForService,
        arrivalDate: String = arrivalDateForService
    ): Boolean {
        return if (departureModel == null || destinationModel == null) {
            false
        } else {
            validateFlightQueryInfoUseCase.invoke(
                departureModel, destinationModel, departureDate, arrivalDate
            )
        }
    }

    fun cacheLastQueriedOrigin(
        originName: String?,
        originId: Int?,
    ) = viewModelScope.launch {
        cacheLastQueriedOriginUseCase(
            originName ?: "",
            originId ?: -1
        )
    }

    fun cacheLastQueriedDestination(
        destinationName: String?,
        destinationId: Int?,
    ) = viewModelScope.launch {
        cacheLastQueriedDestinationUseCase(
            destinationName ?: "",
            destinationId ?: -1
        )
    }

    fun cacheLastQueriedDepartureDate(
        departureDateForService: String?,
        departureDateForUi: String?
    ) = viewModelScope.launch {
        cacheLastQueriedDepartureDateUseCase(
            departureDateForService ?: "",
            departureDateForUi ?: ""
        )
    }

    fun getCachedLastQuery() = viewModelScope.launch {
        getCachedLastQueryUseCase().collect { lastQuery ->
            _getCachedLastQueryStateFlow.update { lastQuery }
        }
    }

    private fun prepareUiDataForBusQueryFragment(): BusQueryUiState {

        val isThereAnyLastCachedQuery = validateCachedQuery(_getCachedLastQueryStateFlow.value)

        val isThereAnyArgumentFromQueryFragment = arguments != null

        val departureDate = getFormattedDateForQuickSelection(isTomorrow = true)
        departureDateForUi = departureDate.dateForUi
        departureDateForService = departureDate.dateForService

        val originName: String?
        val originId: Int?
        val destinationName: String?
        val destinationId: Int?

        Log.d("TQVM", "0")

        // Query data check
        if (isThereAnyArgumentFromQueryFragment) {
            Log.d("TQVM", "1")
            // Populating origin and destination fields with the arguments passed from QueryFragment
            if (arguments?.isOrigin == true) {
                Log.d("TQVM", "2")
                // Origin is passed from QueryFragment
                currentOrigin = arguments?.originLocationModel
                originName = arguments?.originLocationModel?.name
                originId = arguments?.originLocationModel?.id
                // Caching the origin location
                cacheLastQueriedOrigin(
                    originName = arguments?.originLocationModel?.name,
                    originId = arguments?.originLocationModel?.id
                )
                // Destination is fetched from the cache
                if (isThereAnyLastCachedQuery) {
                    Log.d("TQVM", "3")
                    currentDestination = LocationDataUiModel(
                        id = getCachedLastQueryStateFlow.value?.destinationId,
                        name = getCachedLastQueryStateFlow.value?.destinationName,
                        parentId = null,
                        type = null,
                        cityId = null,
                        cityName = null
                    )
                    destinationName = getCachedLastQueryStateFlow.value?.destinationName
                    destinationId = getCachedLastQueryStateFlow.value?.destinationId
                } else {
                    Log.d("TQVM", "4")
                    currentDestination = getBusLocationsStateFlow.value.data?.first()

                    destinationName = currentDestination?.name
                    destinationId = currentDestination?.id

                    cacheLastQueriedDestination(
                        destinationName = destinationName,
                        destinationId = destinationId
                    )
                }
            } else {
                Log.d("TQVM", "5")
                // Destination is passed from QueryFragment
                currentDestination = arguments?.destinationLocationModel
                destinationName = arguments?.destinationLocationModel?.name
                destinationId = arguments?.destinationLocationModel?.id
                // Caching the destination location
                cacheLastQueriedDestination(
                    destinationName = arguments?.destinationLocationModel?.name,
                    destinationId = arguments?.destinationLocationModel?.id
                )
                // Origin is fetched from the cache
                if (isThereAnyLastCachedQuery) {
                    Log.d("TQVM", "6")
                    currentOrigin = LocationDataUiModel(
                        id = getCachedLastQueryStateFlow.value?.originId,
                        name = getCachedLastQueryStateFlow.value?.originName,
                        parentId = null,
                        type = null,
                        cityId = null,
                        cityName = null
                    )
                    originName = getCachedLastQueryStateFlow.value?.originName
                    originId = getCachedLastQueryStateFlow.value?.originId
                } else {
                    Log.d("TQVM", "7")
                    currentOrigin = getBusLocationsStateFlow.value.data?.last()

                    originName = currentOrigin?.name
                    originId = currentOrigin?.id

                    cacheLastQueriedOrigin(
                        originName = originName,
                        originId = originId
                    )
                }
            }
            // Cached data check
        } else if (isThereAnyLastCachedQuery) {
            Log.d("TQVM", "8")
            currentOrigin = LocationDataUiModel(
                id = getCachedLastQueryStateFlow.value?.originId,
                name = getCachedLastQueryStateFlow.value?.originName,
                parentId = null,
                type = null,
                cityId = null,
                cityName = null
            )
            currentDestination = LocationDataUiModel(
                id = getCachedLastQueryStateFlow.value?.originId,
                name = getCachedLastQueryStateFlow.value?.destinationName,
                parentId = null,
                type = null,
                cityId = null,
                cityName = null
            )
            originName = getCachedLastQueryStateFlow.value?.originName
            originId = getCachedLastQueryStateFlow.value?.originId
            destinationName = getCachedLastQueryStateFlow.value?.destinationName
            destinationId = getCachedLastQueryStateFlow.value?.destinationId

            // Randomly fill
        } else {
            Log.d("TQVM", "9")
            currentOrigin = getBusLocationsStateFlow.value.data?.first()
            currentDestination = getBusLocationsStateFlow.value.data?.last()

            originName = currentOrigin?.name
            originId = currentOrigin?.id

            destinationName = currentDestination?.name
            destinationId = currentDestination?.id

            cacheLastQueriedOrigin(
                originName = originName,
                originId = originId
            )

            cacheLastQueriedDestination(
                destinationName = destinationName,
                destinationId = destinationId
            )

        }

        Log.d("TQVM", "10")

        if (getCachedLastQueryStateFlow.value?.departureDateForUi.isNullOrBlank().not()) {
            departureDateForUi = getCachedLastQueryStateFlow.value?.departureDateForUi!!
        }

        if (getCachedLastQueryStateFlow.value?.departureDateForService.isNullOrBlank().not()) {
            departureDateForService = getCachedLastQueryStateFlow.value?.departureDateForService!!
        }

        return BusQueryUiState(
            departureDateForUi = departureDateForUi,
            originName = originName!!,
            originId = originId!!,
            destinationName = destinationName!!,
            destinationId = destinationId!!,
            buttonState = detectQuickSelectionButtonState(
                dateString = departureDateForUi
            )
        )


    }

    fun exposeUiDataForBusQueryFragment() = viewModelScope.launch {
        _busQueryFragmentUiStateFlow.update {
            JourneyResponseState.Success(
                prepareUiDataForBusQueryFragment()
            )
        }
    }

    private fun prepareUiDataForFlightQueryFragment(): FlightQueryUiState {
        val isThereAnyLastCachedQuery = validateCachedQuery(_getCachedLastQueryStateFlow.value)

        val isThereAnyArgumentFromQueryFragment = arguments != null

        val departureDate = getFormattedDateForQuickSelection(isTomorrow = true)
        departureDateForUi = departureDate.dateForUi
        departureDateForService = departureDate.dateForService

        var originName: String? = ""
        var originId: Int? = -1
        var destinationName: String? = ""
        var destinationId: Int? = -1

        // Query data check
        if (isThereAnyArgumentFromQueryFragment) {
            // Populating origin and destination fields with the arguments passed from QueryFragment
            if (arguments?.isOrigin == true) {
                // Origin is passed from QueryFragment
                currentOrigin = arguments?.originLocationModel
                originName = arguments?.originLocationModel?.name
                originId = arguments?.originLocationModel?.id
                // Caching the origin location
                cacheLastQueriedOrigin(
                    originName = arguments?.originLocationModel?.name,
                    originId = arguments?.originLocationModel?.id
                )
                // Destination is fetched from the cache
                if (isThereAnyLastCachedQuery) {
                    currentDestination = LocationDataUiModel(
                        id = getCachedLastQueryStateFlow.value?.destinationId,
                        name = getCachedLastQueryStateFlow.value?.destinationName,
                        parentId = null,
                        type = null,
                        cityId = null,
                        cityName = null
                    )
                    destinationName = getCachedLastQueryStateFlow.value?.destinationName
                    destinationId = getCachedLastQueryStateFlow.value?.destinationId
                } else {
                    currentDestination = getBusLocationsStateFlow.value.data?.first()

                    destinationName = currentDestination?.name
                    destinationId = currentDestination?.id

                    cacheLastQueriedDestination(
                        destinationName = destinationName,
                        destinationId = destinationId
                    )
                }
            } else {
                // Destination is passed from QueryFragment
                currentDestination = arguments?.destinationLocationModel
                destinationName = arguments?.destinationLocationModel?.name
                destinationId = arguments?.destinationLocationModel?.id
                // Caching the destination location
                cacheLastQueriedDestination(
                    destinationName = arguments?.destinationLocationModel?.name,
                    destinationId = arguments?.destinationLocationModel?.id
                )
                // Origin is fetched from the cache
                if (isThereAnyLastCachedQuery) {
                    currentOrigin = LocationDataUiModel(
                        id = getCachedLastQueryStateFlow.value?.originId,
                        name = getCachedLastQueryStateFlow.value?.originName,
                        parentId = null,
                        type = null,
                        cityId = null,
                        cityName = null
                    )
                    originName = getCachedLastQueryStateFlow.value?.originName
                    originId = getCachedLastQueryStateFlow.value?.originId
                } else {
                    currentOrigin = getBusLocationsStateFlow.value.data?.last()

                    originName = currentOrigin?.name
                    originId = currentOrigin?.id

                    cacheLastQueriedOrigin(
                        originName = originName,
                        originId = originId
                    )
                }
            }
            // Cached data check
        } else if (isThereAnyLastCachedQuery) {
            currentOrigin = LocationDataUiModel(
                id = getCachedLastQueryStateFlow.value?.originId,
                name = getCachedLastQueryStateFlow.value?.originName,
                parentId = null,
                type = null,
                cityId = null,
                cityName = null
            )
            currentDestination = LocationDataUiModel(
                id = getCachedLastQueryStateFlow.value?.destinationId,
                name = getCachedLastQueryStateFlow.value?.destinationName,
                parentId = null,
                type = null,
                cityId = null,
                cityName = null
            )
            originName = getCachedLastQueryStateFlow.value?.originName
            originId = getCachedLastQueryStateFlow.value?.originId
            destinationName = getCachedLastQueryStateFlow.value?.destinationName
            destinationId = getCachedLastQueryStateFlow.value?.destinationId

            // Randomly fill
        } else {
            currentOrigin = getBusLocationsStateFlow.value.data?.first()
            currentDestination = getBusLocationsStateFlow.value.data?.last()

            originName = currentOrigin?.name
            originId = currentOrigin?.id

            destinationName = currentDestination?.name
            destinationId = currentDestination?.id

            cacheLastQueriedOrigin(
                originName = originName,
                originId = originId
            )

            cacheLastQueriedDestination(
                destinationName = destinationName,
                destinationId = destinationId
            )

        }

        if (getCachedLastQueryStateFlow.value?.departureDateForUi.isNullOrBlank().not()) {
            departureDateForUi = getCachedLastQueryStateFlow.value?.departureDateForUi!!
        }

        if (getCachedLastQueryStateFlow.value?.departureDateForService.isNullOrBlank().not()) {
            departureDateForService = getCachedLastQueryStateFlow.value?.departureDateForService!!
        }

        val arrivalDate = getFormattedDateForArrivalDateAccordingToDepartureDate(
            departureDate = departureDateForUi
        )

        arrivalDateForUi = arrivalDate.dateForUi
        arrivalDateForService = arrivalDate.dateForService

        return FlightQueryUiState(
            departureDateForUi = departureDateForUi,
            arrivalDateForUi = arrivalDateForUi,
            originName = originName!!,
            originId = originId!!,
            destinationName = destinationName!!,
            destinationId = destinationId!!,
        )
    }

    fun exposeUiDataForFlightQueryFragment() = viewModelScope.launch {
        _flightQueryFragmentUiStateFlow.update {
            JourneyResponseState.Success(
                prepareUiDataForFlightQueryFragment()
            )
        }
    }


    private fun detectQuickSelectionButtonState(
        dateString: String,
        locale: Locale = Locale.getDefault()
    ): Pair<BusQueryQuickSelectionButtonState, String> {
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)
        val uiPattern = if (locale.language == "tr") "d MMMM yyyy EEEE" else "EEEE, d MMMM yyyy"
        val formatter = DateTimeFormatter.ofPattern(uiPattern)

        val date = LocalDate.parse(dateString, formatter)

        return when {
            date.isBefore(today) || date == tomorrow -> {
                Pair(BusQueryQuickSelectionButtonState.TOMORROW_CLICKED, formatter.format(tomorrow))
            }

            date == today -> {
                Pair(BusQueryQuickSelectionButtonState.TODAY_CLICKED, formatter.format(today))
            }

            else -> {
                Pair(BusQueryQuickSelectionButtonState.NOTHING_CLICKED, dateString)
            }
        }
    }


}