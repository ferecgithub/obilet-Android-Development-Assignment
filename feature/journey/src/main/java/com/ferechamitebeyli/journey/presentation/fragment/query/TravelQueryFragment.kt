package com.ferechamitebeyli.journey.presentation.fragment.query

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.FragmentTravelQueryBinding
import com.ferechamitebeyli.journey.presentation.adapter.TravelQueryPagerAdapter
import com.ferechamitebeyli.journey.presentation.argument.JourneyNavArgument
import com.ferechamitebeyli.journey.presentation.util.TravelQueryChildFragmentInteractor
import com.ferechamitebeyli.navigation.Navigator
import com.ferechamitebeyli.navigation.safeNavigate
import com.ferechamitebeyli.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelQueryFragment : BaseFragment<FragmentTravelQueryBinding>(
    FragmentTravelQueryBinding::inflate
), TravelQueryChildFragmentInteractor {
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
    }

    override fun onBusQueryClick(args: JourneyNavArgument) {
        val action = TravelQueryFragmentDirections.actionTravelQueryFragmentToQueryFragment(args)
        (requireActivity() as Navigator).graphSpecificNavigation(R.id.journey_nav_graph)
        (requireActivity() as Navigator).navigateTo(action)
    }

    override fun onFlightQueryClick(args: JourneyNavArgument) {
        //
    }


}