package com.ferechamitebeyli.ui.util

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.ferechamitebeyli.ui.R
import com.ferechamitebeyli.ui.model.DateFormatUiModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object UiHelpers {

    fun ImageView.loadPartnerLogo(partnerId: Int?) {
        this.load(" https://s3.eu-central-1.amazonaws.com/static.obilet.com/images/partner/$partnerId-sm.png") {
            crossfade(true)
            placeholder(R.drawable.ic_bus)
        }
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

    fun getFormattedDate(
        dateString: String,
        locale: Locale = Locale.getDefault()
    ): DateFormatUiModel {
        val date =
            LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
        val uiPattern = if (locale.language == "tr") "d MMMM yyyy EEEE" else "EEEE, d MMMM yyyy"
        val servicePattern = "yyyy-MM-dd'T'HH:mm:ss"

        val uiFormatter = DateTimeFormatter.ofPattern(uiPattern, locale)
        val serviceFormatter = DateTimeFormatter.ofPattern(servicePattern, locale)

        val dateForUi = date.format(uiFormatter)
        val dateForService = date.format(serviceFormatter)

        return DateFormatUiModel(dateForUi, dateForService)
    }

    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return currentDateTime.format(formatter)
    }

    fun getFormattedDateForQuickSelection(
        isTomorrow: Boolean = false,
        locale: Locale = Locale.getDefault()
    ): DateFormatUiModel {
        val date = if (isTomorrow) LocalDateTime.now().plusDays(1) else LocalDateTime.now()
        val uiPattern = if (locale.language == "tr") "d MMMM yyyy EEEE" else "EEEE, d MMMM yyyy"
        val servicePattern = "yyyy-MM-dd"

        val uiFormatter = DateTimeFormatter.ofPattern(uiPattern, locale)
        val serviceFormatter = DateTimeFormatter.ofPattern(servicePattern, locale)

        val dateForUi = date.format(uiFormatter)
        val dateForService = date.format(serviceFormatter)

        return DateFormatUiModel(dateForUi, dateForService)
    }

    fun View.visible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun startRotationAnimation(view: View, context: Context) {
        val imageView = view as ImageView
        val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_180)
        imageView.startAnimation(rotateAnimation)
    }

    fun showDatePicker(context: Context, textView: TextView, locale: Locale = Locale.getDefault(), callBack: (DateFormatUiModel) -> Unit) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(context, { _, year, month, day ->
            val monthString = if (month >= 10) {
                "$month"
            } else {
                "0$month"
            }

            val dayString = if (day >= 10) {
                "$day"
            } else {
                "0$day"
            }

            val selectedDate = LocalDate.of(year, monthString.toInt() + 1, dayString.toInt())
            val uiPattern = if (locale.language == "tr") "d MMMM yyyy EEEE" else "EEEE, d MMMM yyyy"
            val formatter = DateTimeFormatter.ofPattern(uiPattern, locale)
            val formattedDate = selectedDate.format(formatter)
            textView.text = formattedDate
            callBack.invoke(
                DateFormatUiModel(
                    dateForUi = formattedDate,
                    dateForService = context.getString(R.string.label_yearMonthDay, year.toString(), monthString, dayString)
                )
            )

        }, currentYear, currentMonth, currentDay)

        // Disabled older dates
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
        datePicker.show()
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