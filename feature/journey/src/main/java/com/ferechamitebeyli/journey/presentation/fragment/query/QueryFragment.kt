package com.ferechamitebeyli.journey.presentation.fragment.query

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.databinding.FragmentQueryBinding
import com.ferechamitebeyli.journey.presentation.adapter.QueryDialogListAdapter
import com.ferechamitebeyli.navigation.safeNavigate
import com.ferechamitebeyli.ui.base.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class QueryFragment : BaseFragment<FragmentQueryBinding>(
    FragmentQueryBinding::inflate
) {
    private lateinit var queryAdapter: QueryDialogListAdapter
    private val arguments: QueryFragmentArgs by navArgs()


    override fun setUpUi() {
        super.setUpUi()

        binding.recyclerViewQueryDialog.apply {
            queryAdapter = QueryDialogListAdapter { model ->
                navigateToBusQueryFragment(model)
            }
            adapter = queryAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        arguments.args?.locationModelList?.let {
            queryAdapter.submitList(it)
        }

        setUpSearchView()
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
                    text.lowercase().let { lowerCaseText ->
                        model.name?.lowercase(Locale.ROOT)?.contains(lowerCaseText)
                    } == true
                if (ifModelNameContainsInputText) {
                    filteredList.add(model)
                }
            }

            if (filteredList.isEmpty()) {
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    getString(
                        com.ferechamitebeyli.ui.R.string.message_noRecordFound
                    ),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Log.d("QDFRA1", "${filteredList.toList().toTypedArray()}")
                Log.d("QDFRA1", "${filteredList.first()}")
                Log.d("QDFRA1", "${filteredList.last()}")
                queryAdapter.submitList(filteredList)
            }
        }
    }

    private fun navigateToBusQueryFragment(model: LocationDataUiModel) {
        val args = arguments.args?.copy(
            locationModel = model
        )

        val action = QueryFragmentDirections.actionQueryFragmentToTravelQueryFragment(
            args = args
        )

        findNavController().safeNavigate(action)
    }

}