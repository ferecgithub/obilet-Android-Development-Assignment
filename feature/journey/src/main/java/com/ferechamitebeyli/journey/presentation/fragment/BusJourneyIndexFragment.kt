package com.ferechamitebeyli.journey.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.FragmentBusJourneyIndexBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusJourneyIndexFragment : Fragment() {
    private var _binding: FragmentBusJourneyIndexBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusJourneyIndexBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}