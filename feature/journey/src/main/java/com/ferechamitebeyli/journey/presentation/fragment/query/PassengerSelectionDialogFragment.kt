package com.ferechamitebeyli.journey.presentation.fragment.query

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ferechamitebeyli.journey.databinding.LayoutPassengerSelectionBinding
import com.ferechamitebeyli.ui.util.UiHelpers.enable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PassengerSelectionDialogFragment(
    private val onCloseClicked: (Int) -> Unit,
) : BottomSheetDialogFragment() {
    private var adultCount = 0
    private var childrenCount = 0
    private var studentCount = 0
    private var plus65Count = 0
    private var babyCount = 0
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
        setUpUi()

        return binding.root
    }

    private fun setUpUi() {
        binding.textViewAdultAmount.text = adultCount.toString()
        binding.textViewChildrenAmount.text = childrenCount.toString()
        binding.textViewStudentAmount.text = studentCount.toString()
        binding.textView65plusAmount.text = plus65Count.toString()
        binding.textViewBabyAmount.text = babyCount.toString()
    }

    private fun setOnClickListeners() {
        binding.imageViewAdultPlus.setOnClickListener {
            increasePassengerCount(binding.textViewAdultAmount, PassengerType.ADULT)
        }
        binding.imageViewAdultMinus.setOnClickListener {
            decreasePassengerCount(binding.textViewAdultAmount, PassengerType.ADULT)
        }
        binding.imageViewChildrenPlus.setOnClickListener {
            increasePassengerCount(binding.textViewChildrenAmount, PassengerType.CHILDREN)
        }
        binding.imageViewChildrenMinus.setOnClickListener {
            decreasePassengerCount(binding.textViewChildrenAmount, PassengerType.CHILDREN)
        }
        binding.imageViewStudentPlus.setOnClickListener {
            increasePassengerCount(binding.textViewStudentAmount, PassengerType.STUDENT)
        }
        binding.imageViewStudentMinus.setOnClickListener {
            decreasePassengerCount(binding.textViewStudentAmount, PassengerType.STUDENT)
        }
        binding.imageView65plusPlus.setOnClickListener {
            increasePassengerCount(binding.textView65plusAmount, PassengerType.PLUS65)
        }
        binding.imageView65plusMinus.setOnClickListener {
            decreasePassengerCount(binding.textView65plusAmount, PassengerType.PLUS65)
        }
        binding.imageViewBabyPlus.setOnClickListener {
            increasePassengerCount(binding.textViewBabyAmount, PassengerType.BABY)
        }
        binding.imageViewBabyMinus.setOnClickListener {
            decreasePassengerCount(binding.textViewBabyAmount, PassengerType.BABY)
        }

        binding.imageViewPassengerSelectionClose.setOnClickListener {
            onCloseClicked.invoke(passengerCount)
            this.dismiss()
        }
    }

    private fun calculatePassengerCount() =
        adultCount + childrenCount + studentCount + plus65Count + babyCount

    private fun increasePassengerCount(textView: TextView, passengerType: PassengerType) {
        checkIfPassengerAmountExceeds()
        when (passengerType) {
            PassengerType.ADULT -> {
                ++adultCount
                textView.text = adultCount.toString()
            }

            PassengerType.CHILDREN -> {
                ++childrenCount
                textView.text = childrenCount.toString()
            }

            PassengerType.STUDENT -> {
                ++studentCount
                textView.text = studentCount.toString()
            }

            PassengerType.PLUS65 -> {
                ++plus65Count
                textView.text = plus65Count.toString()
            }

            PassengerType.BABY -> {
                ++babyCount
                textView.text = babyCount.toString()
            }
        }
        passengerCount = calculatePassengerCount()
    }

    private fun decreasePassengerCount(textView: TextView, passengerType: PassengerType) {
        checkIfPassengerAmountExceeds()
        when (passengerType) {
            PassengerType.ADULT -> {
                --adultCount
                textView.text = adultCount.toString()
            }

            PassengerType.CHILDREN -> {
                --childrenCount
                textView.text = childrenCount.toString()
            }

            PassengerType.STUDENT -> {
                --studentCount
                textView.text = studentCount.toString()
            }

            PassengerType.PLUS65 -> {
                --plus65Count
                textView.text = plus65Count.toString()
            }

            PassengerType.BABY -> {
                --babyCount
                textView.text = babyCount.toString()
            }
        }
        passengerCount = calculatePassengerCount()
    }

    private fun checkIfPassengerAmountExceeds(amount: Int = 4) {
        val calculatedPassengerCount = calculatePassengerCount()
        if (calculatedPassengerCount >= amount) {
            handleAvailibilityOfPluses(false)
            handleAvailibilityOfMinuses(true)
        } else if (calculatedPassengerCount < 1) {
            handleAvailibilityOfPluses(true)
            handleAvailibilityOfMinuses(false)
        } else {
            handleAvailibilityOfPluses(true)
            handleAvailibilityOfMinuses(true)
        }
    }

    private fun handleAvailibilityOfPluses(isEnabled: Boolean) {
        binding.imageViewAdultPlus.enable(isEnabled)
        binding.imageViewChildrenPlus.enable(isEnabled)
        binding.imageViewStudentPlus.enable(isEnabled)
        binding.imageViewBabyPlus.enable(isEnabled)
        binding.imageView65plusPlus.enable(isEnabled)
    }

    private fun handleAvailibilityOfMinuses(isEnabled: Boolean) {
        binding.imageViewAdultMinus.enable(isEnabled)
        binding.imageViewChildrenMinus.enable(isEnabled)
        binding.imageViewStudentMinus.enable(isEnabled)
        binding.imageViewBabyMinus.enable(isEnabled)
        binding.imageView65plusMinus.enable(isEnabled)
    }

    enum class PassengerType {
        ADULT,
        CHILDREN,
        STUDENT,
        PLUS65,
        BABY
    }
}