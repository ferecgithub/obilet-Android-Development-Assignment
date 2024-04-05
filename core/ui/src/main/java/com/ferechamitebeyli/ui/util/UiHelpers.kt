package com.ferechamitebeyli.ui.util

import android.widget.ImageView
import coil.load
import com.ferechamitebeyli.ui.R
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

object UiHelpers {

    fun ImageView.loadPartnerLogo(partnerId: Int) {
        this.load(" https://s3.eu-central-1.amazonaws.com/static.obilet.com/images/partner/$partnerId-sm.png") {
            crossfade(true)
            placeholder(R.drawable.ic_bus)
        }
    }

    fun formatDate(dateString: String, timezoneIndex: String = "015", desiredDateFormat: String = "yyyy-MM-dd"): String {
        // Microsoft Time Zone Index Value for Turkey Standard Time is "015"
        // Define the input and output date formats
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormatter = DateTimeFormatter.ofPattern(desiredDateFormat)

        // Parse the input date string into a LocalDateTime object with the specified timezone
        val dateTime = LocalDateTime.parse(dateString, inputFormatter)
            .atZone(ZoneId.of("Windows/$timezoneIndex"))
            .toLocalDateTime()

        // Format the LocalDateTime object into the desired format
        return dateTime.format(outputFormatter)
    }

    fun getTomorrowDate(dateString: String, timezoneIndex: String = "015", desiredDateFormat: String = "yyyy-MM-dd"): String {
        // Define the input date format
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormatter = DateTimeFormatter.ofPattern(desiredDateFormat)

        // Parse the input date string into a LocalDateTime object with the specified timezone
        val dateTime = LocalDateTime.parse(dateString, inputFormatter)
            .plusDays(1) // Add one day to get tomorrow's date
            .atZone(ZoneId.of("Windows/$timezoneIndex"))
            .toLocalDateTime()

        // Format the LocalDateTime object into the desired format
        return dateTime.format(outputFormatter)
    }
}