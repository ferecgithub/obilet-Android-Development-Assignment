package com.ferechamitebeyli.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment

/**
 * Created by Ferec Hamitbeyli on 6.04.2024.
 */

interface Navigator{
    var navHostFragment: NavHostFragment
    var navController: NavController
    fun navigateTo(action: NavDirections)
    fun navigateTo(destination: Int)
    fun navigateTo(uri: Uri)
    fun navigateTo(action: NavDirections, graphId:Int){
        val myNavHostFragment: NavHostFragment = navHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph = inflater.inflate(graphId)
        myNavHostFragment.navController.graph = graph

        navigateTo(action)
    }
    fun navigateTo(destination: Int, graphId:Int){
        val myNavHostFragment: NavHostFragment = navHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph = inflater.inflate(graphId)
        myNavHostFragment.navController.graph = graph

        navigateTo(destination)
    }
    fun navigateTo(uri: Uri, graphId:Int){
        val myNavHostFragment: NavHostFragment = navHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph = inflater.inflate(graphId)
        myNavHostFragment.navController.graph = graph
        navigateTo(uri)
    }

    fun graphSpecificNavigation(graphId:Int)
}