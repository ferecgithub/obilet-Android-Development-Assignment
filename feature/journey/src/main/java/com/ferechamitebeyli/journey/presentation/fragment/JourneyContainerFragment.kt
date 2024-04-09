package com.ferechamitebeyli.journey.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.FragmentJourneyContainerBinding
import com.ferechamitebeyli.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JourneyContainerFragment : BaseFragment<FragmentJourneyContainerBinding>(
    FragmentJourneyContainerBinding::inflate
) {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigationComponents()
    }

    private fun setupNavigationComponents() {
        navHostFragment =
            childFragmentManager.findFragmentById(R.id.fragmentContainerView_journey) as NavHostFragment
        navController = navHostFragment.navController
    }

}