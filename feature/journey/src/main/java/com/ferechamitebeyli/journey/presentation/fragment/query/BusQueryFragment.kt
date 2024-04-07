package com.ferechamitebeyli.journey.presentation.fragment.query

import androidx.fragment.app.viewModels
import com.ferechamitebeyli.journey.databinding.FragmentBusQueryBinding
import com.ferechamitebeyli.journey.presentation.viewmodel.BusQueryViewModel
import com.ferechamitebeyli.ui.base.BaseFragment
import com.ferechamitebeyli.ui.util.UiHelpers.getFormattedDateForQuickSelection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BusQueryFragment : BaseFragment<FragmentBusQueryBinding>(
    FragmentBusQueryBinding::inflate
) {
    private val viewModel: BusQueryViewModel by viewModels()

    override fun setUpUi() {
        super.setUpUi()

        // Set default date as tomorrow
        setDateForQuickSelection(isTomorrow = true)

        // Assigning date strings for Ui and to a variable in ViewModel to send to the service later.
        binding.radioButtonBusQueryToday.setOnCheckedChangeListener { compoundButton, isChecked ->
            setDateForQuickSelection(isChecked.not())
        }

        binding.radioButtonBusQueryTomorrow.setOnCheckedChangeListener { compoundButton, isChecked ->
            setDateForQuickSelection(isChecked)
        }
    }

    private fun setDateForQuickSelection(isTomorrow: Boolean) {
        val date = getFormattedDateForQuickSelection(
            isTomorrow = isTomorrow
        )
        binding.textViewBusQueryDepartureDate.text = date.dateForUi
        viewModel.departureDateForService = date.dateForService
    }


}