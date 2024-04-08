package com.ferechamitebeyli.obiletandroidassignment

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.ferechamitebeyli.navigation.Navigator
import com.ferechamitebeyli.obiletandroidassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {
    private lateinit var binding: ActivityMainBinding
    override lateinit var navHostFragment: NavHostFragment
    override lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationComponents()
    }

    override fun navigateTo(action: NavDirections) {
        navController.navigate(action)
    }

    override fun navigateTo(destination: Int) {
        //Using fragmentId
        navController.navigate(destination)

    }

    override fun navigateTo(uri: Uri) {
        //Using uri directly
        navController.navigate(uri)
        //or using NavDeepLinkRequest
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
        navController.navigate(request)

    }

    override fun graphSpecificNavigation(graphId: Int) {
        val myNavHostFragment: NavHostFragment = navHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph = inflater.inflate(graphId)
        navController.graph.addAll(graph)
    }


    private fun setupNavigationComponents() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView_main) as NavHostFragment
        navController = navHostFragment.navController
    }

}