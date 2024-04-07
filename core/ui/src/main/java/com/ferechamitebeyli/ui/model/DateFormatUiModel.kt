package com.ferechamitebeyli.ui.model

// UUi model for acquiring dates in different formats. Dates shown on UI (for readability reasons) and date formats required by service are different.
data class DateFormatUiModel(
    val dateForUi: String,
    val dateForService: String
)
