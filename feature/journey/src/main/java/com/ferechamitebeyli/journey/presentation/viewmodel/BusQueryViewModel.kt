package com.ferechamitebeyli.journey.presentation.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BusQueryViewModel @Inject constructor() : ViewModel() {

    var departureDateForService: String? = null

}