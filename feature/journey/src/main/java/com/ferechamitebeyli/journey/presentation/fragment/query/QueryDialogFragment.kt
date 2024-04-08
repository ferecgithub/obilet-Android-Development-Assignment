package com.ferechamitebeyli.journey.presentation.fragment.query

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferechamitebeyli.data.model.location.LocationDataUiModel
import com.ferechamitebeyli.journey.databinding.DialogFragmentQueryBinding
import com.ferechamitebeyli.journey.presentation.adapter.QueryDialogListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.Locale

class QueryDialogFragment(
    private val isOrigin: Boolean,
    private val modelList: List<LocationDataUiModel>,
    private val onQueryItemClicked: (LocationDataUiModel, Boolean) -> Unit,
) : BottomSheetDialogFragment() {
    private var _binding: DialogFragmentQueryBinding? = null

    val binding: DialogFragmentQueryBinding get() = _binding!!

    private lateinit var queryAdapter: QueryDialogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentQueryBinding.inflate(inflater, container, false)

        setUpUi()
        setUpSearchView()

        return binding.root
    }

    private fun setUpUi() {
        binding.recyclerViewQueryDialog.apply {
            queryAdapter = QueryDialogListAdapter { model ->
                onQueryItemClicked.invoke(model, isOrigin)
            }
            adapter = queryAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

        Log.d("QDFRA", "${modelList.toList().toTypedArray()}")
        Log.d("QDFRA", "${modelList.first()}")
        Log.d("QDFRA", "${modelList.last()}")
        Log.d("QDFRA123", "${::queryAdapter.isInitialized}")
        queryAdapter.submitList(modelList)
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
            modelList.forEach { model ->
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
}