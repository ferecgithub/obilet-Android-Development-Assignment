package com.ferechamitebeyli.data.util

import android.content.Context
import androidx.annotation.StringRes

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

sealed class UiText {
    data class DynamicString(val value: String?) : UiText()
    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()

    fun asString(context: Context): String? {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}