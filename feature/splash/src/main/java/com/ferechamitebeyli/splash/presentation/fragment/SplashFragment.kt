package com.ferechamitebeyli.splash.presentation.fragment

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ferechamitebeyli.navigation.safeNavigate
import com.ferechamitebeyli.network.dto.client.getsession.request.Application
import com.ferechamitebeyli.network.dto.client.getsession.request.Connection
import com.ferechamitebeyli.network.dto.client.getsession.request.GetSessionRequestModel
import com.ferechamitebeyli.splash.R
import com.ferechamitebeyli.splash.databinding.FragmentSplashBinding
import com.ferechamitebeyli.splash.presentation.state.SplashResponseState
import com.ferechamitebeyli.splash.presentation.state.SplashScreenState
import com.ferechamitebeyli.splash.presentation.viewmodel.SplashViewModel
import com.ferechamitebeyli.ui.base.BaseFragment
import com.ferechamitebeyli.ui.util.UiHelpers.collectFlowWithFragmentLifecycle
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(
    FragmentSplashBinding::inflate
) {
    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       getSession()

    }
    override fun setUpUi() {
        super.setUpUi()
        progressBar = binding.progressBar
    }

    private fun getSession() {
        val model = GetSessionRequestModel(
            connection = Connection(
                ipAddress = null
            ),
            application = Application(
                equipmentId = getUniqueDeviceIdentifier(),
                version = "3.1.0.0"
            ),
            type = 3
        )
        viewModel.getSession(model)
    }

    override fun observeFlows() {
        viewModel.getSessionStateFlow.collectFlowWithFragmentLifecycle(
            this@SplashFragment
        ) { state ->
            when (state) {
                is SplashResponseState.Error -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireContext(),
                        binding.root,
                        state.error?.asString(requireContext()) ?: getString(
                            com.ferechamitebeyli.network.R.string.message_safeApiCall_operationFailed
                        ),
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                is SplashResponseState.Idle -> {/* NO-OP */
                }

                is SplashResponseState.Loading -> {
                    showProgressBar()
                }

                is SplashResponseState.Success -> {
                    hideProgressBar()
                    Snackbar.make(
                        requireContext(),
                        binding.root,
                        getString(R.string.message_welcome),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        viewModel.splashScreenStateFlow.collectFlowWithFragmentLifecycle(
            this@SplashFragment
        ) {
            if (it == SplashScreenState.SessionIsEstablished) {
                navigateToTravelQueryFragment()
            }

        }
    }

    @SuppressLint("HardwareIds")
    private fun getUniqueDeviceIdentifier(): String {
        return Settings.Secure.getString(
            requireContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    private fun navigateToTravelQueryFragment() {
        findNavController().safeNavigate(com.ferechamitebeyli.navigation.R.id.global_action_journey_nav_graph)
    }

}