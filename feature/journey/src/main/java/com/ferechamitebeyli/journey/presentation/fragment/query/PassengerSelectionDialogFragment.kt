package com.ferechamitebeyli.journey.presentation.fragment.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ferechamitebeyli.journey.databinding.LayoutPassengerSelectionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PassengerSelectionDialogFragment(
    private val onCloseClicked: (Int) -> Unit,
) : BottomSheetDialogFragment() {
    private var passengerCount = 0

    private var _binding: LayoutPassengerSelectionBinding? = null

    val binding: LayoutPassengerSelectionBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutPassengerSelectionBinding.inflate(inflater, container, false)

        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners() {
        binding.imageViewAdultPlus.setOnClickListener {
            increasePassengerCount(binding.textViewAdultAmount)
        }
        binding.imageViewAdultMinus.setOnClickListener {
            decreasePassengerCount(binding.textViewAdultAmount)
        }
        binding.imageViewChildrenPlus.setOnClickListener {
            increasePassengerCount(binding.textViewChildrenAmount)
        }
        binding.imageViewChildrenMinus.setOnClickListener {
            decreasePassengerCount(binding.textViewChildrenAmount)
        }
        binding.imageViewStudentPlus.setOnClickListener {
            increasePassengerCount(binding.textViewStudentAmount)
        }
        binding.imageViewStudentMinus.setOnClickListener {
            decreasePassengerCount(binding.textViewStudentAmount)
        }
        binding.imageView65plusPlus.setOnClickListener {
            increasePassengerCount(binding.textView65plusAmount)
        }
        binding.imageView65plusMinus.setOnClickListener {
            decreasePassengerCount(binding.textView65plusAmount)
        }
        binding.imageViewBabyPlus.setOnClickListener {
            increasePassengerCount(binding.textViewBabyAmount)
        }
        binding.imageViewBabyMinus.setOnClickListener {
            decreasePassengerCount(binding.textViewBabyAmount)
        }

        binding.imageViewPassengerSelectionClose.setOnClickListener {
            onCloseClicked.invoke(passengerCount)
        }
    }

    private fun increasePassengerCount(textView: TextView) {
        checkIfPassengerAmountExceeds()
        passengerCount++
        textView.text = passengerCount.toString()
    }

    private fun decreasePassengerCount(textView: TextView) {
        checkIfPassengerAmountExceeds()
        passengerCount--
        textView.text = passengerCount.toString()
    }

    private fun checkIfPassengerAmountExceeds(amount: Int = 4) {
        if (passengerCount >= amount) {
            handleAvailibilityOfPluses(false)
            handleAvailibilityOfMinuses(true)
        } else if (passengerCount <= 0) {
            handleAvailibilityOfPluses(true)
            handleAvailibilityOfMinuses(false)
        }
    }

    private fun handleAvailibilityOfPluses(isEnabled: Boolean) {
        binding.imageViewAdultPlus.isEnabled = isEnabled
        binding.imageViewChildrenPlus.isEnabled = isEnabled
        binding.imageViewStudentPlus.isEnabled = isEnabled
        binding.imageViewBabyPlus.isEnabled = isEnabled
        binding.imageView65plusPlus.isEnabled = isEnabled
    }

    private fun handleAvailibilityOfMinuses(isEnabled: Boolean) {
        binding.imageViewAdultMinus.isEnabled = isEnabled
        binding.imageViewChildrenMinus.isEnabled = isEnabled
        binding.imageViewStudentMinus.isEnabled = isEnabled
        binding.imageViewBabyMinus.isEnabled = isEnabled
        binding.imageView65plusMinus.isEnabled = isEnabled
    }
}