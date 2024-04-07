package com.ferechamitebeyli.journey.presentation.fragment.query

import com.ferechamitebeyli.journey.databinding.FragmentFlightQueryBinding
import com.ferechamitebeyli.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightQueryFragment : BaseFragment<FragmentFlightQueryBinding>(
    FragmentFlightQueryBinding::inflate
) {

    override fun setUpUi() {
        super.setUpUi()

        // Setting initial passenger value to 0
        binding.textViewFlightQueryPassenger.text =
            getString(com.ferechamitebeyli.ui.R.string.label_passengerWithArgs, "0")
    }

}