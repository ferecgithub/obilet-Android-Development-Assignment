package com.ferechamitebeyli.journey.presentation.fragment.query

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ferechamitebeyli.journey.R
import com.ferechamitebeyli.journey.databinding.FragmentTravelQueryBinding
import com.ferechamitebeyli.journey.presentation.adapter.TravelQueryPagerAdapter
import com.ferechamitebeyli.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TravelQueryFragment : BaseFragment<FragmentTravelQueryBinding>(
    FragmentTravelQueryBinding::inflate
) {
    private lateinit var adapter: TravelQueryPagerAdapter
    override fun setUpUi() {
        super.setUpUi()

        val fragmentList = arrayListOf<Fragment>(
            BusQueryFragment(),
            FlightQueryFragment()
        )

        adapter = TravelQueryPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.tabLayoutTravelQuery.addTab(
            binding.tabLayoutTravelQuery.newTab()
                .setText(getString(com.ferechamitebeyli.ui.R.string.label_busTicket))
        )
        binding.tabLayoutTravelQuery.addTab(
            binding.tabLayoutTravelQuery.newTab()
                .setText(getString(com.ferechamitebeyli.ui.R.string.label_airplaneTicket))
        )

        binding.viewPagerTravelQuery.adapter = adapter

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

        binding.viewPagerTravelQuery.registerOnPageChangeCallback(object: OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tabLayoutTravelQuery.selectTab(binding.tabLayoutTravelQuery.getTabAt(position))
            }
        })
    }

}