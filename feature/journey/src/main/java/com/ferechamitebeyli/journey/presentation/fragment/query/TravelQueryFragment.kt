package com.ferechamitebeyli.journey.presentation.fragment.query

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ferechamitebeyli.journey.databinding.FragmentTravelQueryBinding
import com.ferechamitebeyli.journey.presentation.adapter.TravelQueryPagerAdapter
import com.ferechamitebeyli.journey.presentation.viewmodel.TravelQueryViewModel
import com.ferechamitebeyli.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelQueryFragment : BaseFragment<FragmentTravelQueryBinding>(
    FragmentTravelQueryBinding::inflate
) {
    private val viewModel: TravelQueryViewModel by viewModels()
    private val arguments: TravelQueryFragmentArgs by navArgs()
    private lateinit var adapter: TravelQueryPagerAdapter

    override fun setUpUi() {
        super.setUpUi()

        val fragmentList = arrayListOf<Fragment>(
            BusQueryFragment(),
            FlightQueryFragment()
        )

        adapter = TravelQueryPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )

        binding.viewPagerTravelQuery.adapter = adapter

        TabLayoutMediator(
            binding.tabLayoutTravelQuery,
            binding.viewPagerTravelQuery
        ) { tab, position ->
            tab.text = when (position) {
                0 -> getString(com.ferechamitebeyli.ui.R.string.label_busTicket)
                1 -> getString(com.ferechamitebeyli.ui.R.string.label_airplaneTicket)
                else -> ""
            }
        }.attach()

        binding.tabLayoutTravelQuery.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { position ->
                    binding.viewPagerTravelQuery.currentItem = position
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.viewPagerTravelQuery.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayoutTravelQuery.selectTab(
                    binding.tabLayoutTravelQuery.getTabAt(
                        position
                    )
                )
            }
        })

        arguments.args?.let { args ->
            viewModel.arguments = args

            viewModel.arguments?.lastSelectedTabIndex?.let {
                binding.tabLayoutTravelQuery.getTabAt(
                    it
                )
            }
        }
    }


}