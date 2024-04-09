package com.ferechamitebeyli.journey.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TravelQueryPagerAdapter(
    list: ArrayList<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList = list
    override fun getItemCount(): Int = fragmentList.size

    /**
     * Creates a fragment according to the position.
     *
     * @param position Index position of fragment.
     * @return Fragment to be created.
     */
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}