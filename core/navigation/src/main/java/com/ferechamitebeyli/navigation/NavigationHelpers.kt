package com.ferechamitebeyli.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDirections

/**
 * Created by Ferec Hamitbeyli on 4.04.2024.
 */

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.let { navigate(direction) }
}

fun NavController.safeNavigate(id: Int) {
    currentDestination?.getAction(id)?.let { navigate(id) }
}