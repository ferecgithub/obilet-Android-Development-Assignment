package com.ferechamitebeyli.ui.util

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.ferechamitebeyli.ui.R
import com.ferechamitebeyli.ui.model.DateFormatUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object UiHelpers {

    fun ImageView.loadPartnerLogo(partnerId: Int?) {
        this.load("https://s3.eu-central-1.amazonaws.com/static.obilet.com/images/partner/$partnerId-sm.png") {
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

    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        return currentDateTime.format(formatter)
    }

    fun getFormattedDateForQuickSelection(
        isTomorrow: Boolean = false,
        locale: Locale = Locale.getDefault()
    ): DateFormatUiModel {
        val date = if (isTomorrow) LocalDate.now().plusDays(1) else LocalDate.now()
        val uiPattern = if (locale.language == "tr") "d MMMM yyyy EEEE" else "EEEE, d MMMM yyyy"
        val servicePattern = "yyyy-MM-dd"

        val uiFormatter = DateTimeFormatter.ofPattern(uiPattern, locale)
        val serviceFormatter = DateTimeFormatter.ofPattern(servicePattern, locale)

        val dateForUi = date.format(uiFormatter)
        val dateForService = date.format(serviceFormatter)

        return DateFormatUiModel(dateForUi, dateForService)
    }

    fun getFormattedDateForArrivalDateAccordingToDepartureDate(
        departureDate: String,
        daysAfter: Long = 1,
        locale: Locale = Locale.getDefault()
    ): DateFormatUiModel {
        val uiPattern = if (locale.language == "tr") "d MMMM yyyy EEEE" else "EEEE, d MMMM yyyy"
        val servicePattern = "yyyy-MM-dd"

        val formatter = DateTimeFormatter.ofPattern(uiPattern, Locale.ENGLISH)
        val parsedDate = LocalDate.parse(departureDate, formatter)
        val adjustedDate = parsedDate.plusDays(daysAfter)

        val uiFormatter = DateTimeFormatter.ofPattern(uiPattern, locale)
        val serviceFormatter = DateTimeFormatter.ofPattern(servicePattern, locale)

        val dateForUi = adjustedDate.format(uiFormatter)
        val dateForService = adjustedDate.format(serviceFormatter)

        return DateFormatUiModel(dateForUi, dateForService)
    }

    fun selectQuickSelectButtonInitially(
        dateString: String,
        todayButton: RadioButton,
        tomorrowBottom: RadioButton,
        locale: Locale = Locale.getDefault()
    ): String {
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)
        val uiPattern = if (locale.language == "tr") "d MMMM yyyy EEEE" else "EEEE, d MMMM yyyy"
        val formatter = DateTimeFormatter.ofPattern(uiPattern)

        val date = LocalDate.parse(dateString, formatter)

        return when {
            date.isBefore(today) || date == tomorrow -> {
                tomorrowBottom.isChecked
                formatter.format(tomorrow)
            }

            date == today -> {
                todayButton.isChecked
                formatter.format(today)
            }

            else -> {
                todayButton.isChecked = false
                tomorrowBottom.isChecked = false
                dateString
            }
        }
    }

    fun View.visible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun View.enable(enabled: Boolean) {
        isEnabled = enabled
        alpha = if (enabled) 1f else 0.5f
    }

    fun startRotationAnimation(view: View, context: Context) {
        val imageView = view as ImageView
        val rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate_180)
        imageView.startAnimation(rotateAnimation)
    }

    fun showDatePicker(
        context: Context,
        textView: TextView,
        locale: Locale = Locale.getDefault(),
        callBack: (DateFormatUiModel) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(context, { _, year, month, day ->
            val monthString = if (month >= 10) {
                "${month + 1}"
            } else {
                "0${month + 1}"
            }

            val dayString = if (day >= 10) {
                "$day"
            } else {
                "0$day"
            }

            val selectedDate = LocalDate.of(year, monthString.toInt(), dayString.toInt())
            val uiPattern = if (locale.language == "tr") "d MMMM yyyy EEEE" else "EEEE, d MMMM yyyy"
            val formatter = DateTimeFormatter.ofPattern(uiPattern, locale)
            val formattedDate = selectedDate.format(formatter)
            textView.text = formattedDate
            callBack.invoke(
                DateFormatUiModel(
                    dateForUi = formattedDate,
                    dateForService = context.getString(
                        R.string.label_yearMonthDay,
                        year.toString(),
                        monthString,
                        dayString
                    )
                )
            )

        }, currentYear, currentMonth, currentDay)

        // Disabled older dates
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
        datePicker.show()
    }

    fun isArrivalDateAfterDeparture(departureDate: String, arrivalDate: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val departureDateParsed = dateFormat.parse(departureDate)
        val arrivalDateParsed = dateFormat.parse(arrivalDate)

        return arrivalDateParsed.after(departureDateParsed)
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