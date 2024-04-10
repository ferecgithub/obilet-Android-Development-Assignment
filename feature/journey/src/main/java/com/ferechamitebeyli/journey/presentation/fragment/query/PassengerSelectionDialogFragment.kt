package com.ferechamitebeyli.journey.presentation.fragment.query

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ferechamitebeyli.journey.databinding.LayoutPassengerSelectionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerSelectionDialogFragment(
    private val onCloseClicked: (UInt) -> Unit,
) : BottomSheetDialogFragment() {

    private var _binding: LayoutPassengerSelectionBinding? = null

    private val binding: LayoutPassengerSelectionBinding get() = _binding!!

    private lateinit var listPassengers: List<PassengerRow>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutPassengerSelectionBinding.inflate(inflater, container, false)

        listPassengers = arrayListOf(
            PassengerRow(
                count = 0U,
                textView = binding.textViewAdultAmount,
                plusOperatorView = binding.imageViewAdultPlus,
                minusOperatorView = binding.imageViewAdultMinus,
                passengerType = PassengerType.ADULT
            ),
            PassengerRow(
                count = 0U,
                textView = binding.textViewChildrenAmount,
                plusOperatorView = binding.imageViewChildrenPlus,
                minusOperatorView = binding.imageViewChildrenMinus,
                passengerType = PassengerType.CHILDREN
            ),
            PassengerRow(
                count = 0U,
                textView = binding.textViewStudentAmount,
                plusOperatorView = binding.imageViewStudentPlus,
                minusOperatorView = binding.imageViewStudentMinus,
                passengerType = PassengerType.STUDENT
            ),
            PassengerRow(
                count = 0U,
                textView = binding.textView65plusAmount,
                plusOperatorView = binding.imageView65plusPlus,
                minusOperatorView = binding.imageView65plusMinus,
                passengerType = PassengerType.PLUS65
            ),
            PassengerRow(
                count = 0U,
                textView = binding.textViewBabyAmount,
                plusOperatorView = binding.imageViewBabyPlus,
                minusOperatorView = binding.imageViewBabyMinus,
                passengerType = PassengerType.BABY
            ),
        )

        setOnClickListeners()
        setUpUi()

        return binding.root
    }

    private fun setUpUi() {
        binding.textViewAdultAmount.text =
            listPassengers.find { it.passengerType == PassengerType.ADULT }?.count.toString()
        binding.textViewChildrenAmount.text =
            listPassengers.find { it.passengerType == PassengerType.CHILDREN }?.count.toString()
        binding.textViewStudentAmount.text =
            listPassengers.find { it.passengerType == PassengerType.STUDENT }?.count.toString()
        binding.textView65plusAmount.text =
            listPassengers.find { it.passengerType == PassengerType.PLUS65 }?.count.toString()
        binding.textViewBabyAmount.text =
            listPassengers.find { it.passengerType == PassengerType.BABY }?.count.toString()
    }

    private fun setOnClickListeners() {
        binding.imageViewAdultPlus.setOnClickListener {
            increasePassengerCount(
                passengerType = PassengerType.ADULT
            )
        }
        binding.imageViewAdultMinus.setOnClickListener {
            decreasePassengerCount(
                passengerType = PassengerType.ADULT

            )
        }
        binding.imageViewChildrenPlus.setOnClickListener {
            increasePassengerCount(
                passengerType = PassengerType.CHILDREN
            )
        }
        binding.imageViewChildrenMinus.setOnClickListener {
            decreasePassengerCount(
                passengerType = PassengerType.CHILDREN
            )
        }
        binding.imageViewStudentPlus.setOnClickListener {
            increasePassengerCount(
                passengerType = PassengerType.STUDENT
            )
        }
        binding.imageViewStudentMinus.setOnClickListener {
            decreasePassengerCount(
                passengerType = PassengerType.STUDENT
            )
        }
        binding.imageView65plusPlus.setOnClickListener {
            increasePassengerCount(
                passengerType = PassengerType.PLUS65
            )
        }
        binding.imageView65plusMinus.setOnClickListener {
            decreasePassengerCount(
                passengerType = PassengerType.PLUS65
            )
        }
        binding.imageViewBabyPlus.setOnClickListener {
            increasePassengerCount(
                passengerType = PassengerType.BABY
            )
        }
        binding.imageViewBabyMinus.setOnClickListener {
            decreasePassengerCount(
                passengerType = PassengerType.BABY
            )
        }

        binding.imageViewPassengerSelectionClose.setOnClickListener {
            onCloseClicked.invoke(calculatePassengerCount(listPassengers))
            this.dismiss()
        }
    }

    private fun calculatePassengerCount(listOfPassengers: List<PassengerRow>) =
        (listOfPassengers.sumOf { it.count })

    private fun increasePassengerCount(
        maxAmount: UInt = 4U,
        passengerType: PassengerType
    ) {
        val passenger = listPassengers.find { it.passengerType == passengerType }!!
        passenger.addOneToCount(maxAmount, calculatePassengerCount(listPassengers))
        passenger.textView.text = passenger.count.toString()
    }

    private fun decreasePassengerCount(
        passengerType: PassengerType
    ) {
        val passenger = listPassengers.find { it.passengerType == passengerType }!!
        passenger.subtractOneFromCount()
        passenger.textView.text = passenger.count.toString()
    }

    enum class PassengerType {
        ADULT,
        CHILDREN,
        STUDENT,
        PLUS65,
        BABY
    }

    data class PassengerRow(
        var count: UInt = 0U,
        var textView: TextView,
        var plusOperatorView: ImageView,
        var minusOperatorView: ImageView,
        val passengerType: PassengerType
    ) {

        fun addOneToCount(maxAmount: UInt, currentTotalCount: UInt) {
            if (currentTotalCount < maxAmount) {
                this.count++
            }

        }

        fun subtractOneFromCount() {
            if (count > 0U) {
                this.count--
            }
        }
    }
}