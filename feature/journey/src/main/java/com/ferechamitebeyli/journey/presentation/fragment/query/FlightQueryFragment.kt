package com.ferechamitebeyli.journey.presentation.fragment.query

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.FragmentBusJourneyIndexBinding
import com.ferechamitebeyli.journey.databinding.FragmentFlightQueryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightQueryFragment : Fragment() {
    private var _binding: FragmentFlightQueryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightQueryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}