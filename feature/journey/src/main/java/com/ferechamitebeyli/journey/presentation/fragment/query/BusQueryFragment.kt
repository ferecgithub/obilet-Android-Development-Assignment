package com.ferechamitebeyli.journey.presentation.fragment.query

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.FragmentBusJourneyIndexBinding
import com.ferechamitebeyli.journey.databinding.FragmentBusQueryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusQueryFragment : Fragment() {
    private var _binding: FragmentBusQueryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBusQueryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}