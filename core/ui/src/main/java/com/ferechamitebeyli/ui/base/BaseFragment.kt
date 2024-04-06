package com.ferechamitebeyli.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ferechamitebeyli.ui.util.UiComponents

/**
 * Created by Ferec Hamitbeyli on 5.04.2024.
 */

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment(), UiComponents {
    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)

        setUpUi()
        setOnClickListeners()
        observeFlows()

        return binding.root
    }

    override fun showProgressBar() {
        if (::progressBar.isInitialized) {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        if (::progressBar.isInitialized) {
            progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}