package com.ferechamitebeyli.ui.util

import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.ferechamitebeyli.ui.R
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

object UiHelpers {

    fun ImageView.loadPartnerLogo(partnerId: Int?) {
        this.load(" https://s3.eu-central-1.amazonaws.com/static.obilet.com/images/partner/$partnerId-sm.png") {
            crossfade(true)
            placeholder(R.drawable.ic_bus)
        }
    }

    fun formatDate(
        dateString: String,
        timezoneIndex: String = "015",
        desiredDateFormat: String = "yyyy-MM-dd"
    ): String {
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

    fun formatTime(time: String): String {
        val localTime = LocalTime.parse(time)
        val formatter = DateTimeFormatter.ofPattern("H:mm")

        return localTime.format(formatter)
    }

    fun formatDateToTime(date: String): String {
        val localDateTime = LocalDateTime.parse(date)
        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        return localDateTime.format(formatter)
    }

    fun getTomorrowDate(
        dateString: String,
        timezoneIndex: String = "015",
        desiredDateFormat: String = "yyyy-MM-dd"
    ): String {
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

    fun View.visible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun <T> Flow<T>.collectFlowWithFragmentLifecycle(
        fragment: Fragment,
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        onCollect: (T) -> Unit
    ) {
        fragment.viewLifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(fragment.viewLifecycleOwner.lifecycle, lifecycleState)
                .collect {
                    onCollect(it)
                }
        }
    }


}