package com.ferechamitebeyli.data.model.location

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationDataUiModel(
    val id: Int?,
    val parentId: Int?,
    val type: String?,
    val name: String?,
    val cityId: Int?,
    val cityName: String?,
): Parcelable