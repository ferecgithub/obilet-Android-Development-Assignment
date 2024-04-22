package com.ferechamitebeyli.journey.presentation.fragment.query

import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.data.util.OnItemClickListener
import com.ferechamitebeyli.journey.databinding.FragmentQueryBinding
import com.ferechamitebeyli.journey.presentation.adapter.QueryDialogListAdapter
import com.ferechamitebeyli.journey.presentation.argument.JourneyNavArgument
import com.ferechamitebeyli.navigation.safeNavigate
import com.ferechamitebeyli.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class QueryFragment : BaseFragment<FragmentQueryBinding>(
    FragmentQueryBinding::inflate
), OnItemClickListener<LocationDataUiModel> {
    private lateinit var queryAdapter: QueryDialogListAdapter
    private val arguments: QueryFragmentArgs by navArgs()


    override fun setUpUi() {
        super.setUpUi()

        binding.recyclerViewQueryDialog.apply {
            queryAdapter = QueryDialogListAdapter(this@QueryFragment)
            adapter = queryAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        arguments.args?.locationModelList?.let {
            queryAdapter.submitList(it)
        }

        setUpSearchView()
    }

    override fun setOnClickListeners() {
        super.setOnClickListeners()

        binding.imageViewQueryDialogClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setUpSearchView() {
        // To guarantee it will not auto-focus the editText.
        binding.searchViewQueryDialog.clearFocus()

        binding.searchViewQueryDialog.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(text: String?) {
        val filteredList = ArrayList<LocationDataUiModel>()
        text?.let {
            if (arguments.args?.locationModelList == null) return

            arguments.args?.locationModelList?.forEach { model ->

                val ifModelNameContainsInputText =
                    text.lowercase().trim().let { lowerCaseText ->
                        model.name?.trim()?.lowercase(Locale.getDefault())?.contains(lowerCaseText)
                    } == true
                if (ifModelNameContainsInputText) {
                    filteredList.add(model)
                }
            }

            queryAdapter.submitList(filteredList)
        }
    }

    private fun navigateToTravelQueryFragment(argument: JourneyNavArgument?) {
        val action = QueryFragmentDirections.actionQueryFragmentToTravelQueryFragment(
            args = argument
        )
        findNavController().safeNavigate(action)
    }

    override fun onItemClick(position: Int, model: LocationDataUiModel) {
        navigateToTravelQueryFragment(
            if (arguments.args?.isOrigin == true) {
                arguments.args?.copy(
                    originLocationModel = model
                )
            } else {
                arguments.args?.copy(
                    destinationLocationModel = model
                )
            }

        )
    }

}