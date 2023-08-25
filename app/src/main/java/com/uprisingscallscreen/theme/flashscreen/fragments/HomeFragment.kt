package com.uprisingscallscreen.theme.flashscreen.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uprisingscallscreen.theme.flashscreen.MainActivity
import com.uprisingscallscreen.theme.flashscreen.callertheme.ui.fragment.CallScreenLiveThemeFragment
import com.uprisingscallscreen.theme.flashscreen.callertheme.ui.fragment.CallScreenThemeFragment
import com.uprisingscallscreen.theme.flashscreen.callertheme.ui.fragment.CallScreenWallpaperFragment
import com.uprisingscallscreen.theme.flashscreen.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : BaseFragment() {

    private lateinit var tabListThemesType: ArrayList<ThemeOptionsModel>
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var myActivity: MainActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as MainActivity

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        fragmentHomeBinding.viewPagerThemesOption.isUserInputEnabled = false
        tabListThemesType = arrayListOf()
        tabListThemesType.add(ThemeOptionsModel("Theme"))
        tabListThemesType.add(ThemeOptionsModel("Live Theme"))
        tabListThemesType.add(ThemeOptionsModel("Wallpaper"))
        return fragmentHomeBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        with(fragmentHomeBinding)
        {
            tabListThemesType.forEach {
                val tab = tabLayoutThemesOption.newTab()
                tab.text = it.name
                tabLayoutThemesOption.addTab(tab)

            }

            val adapter = ViewPagerAdapter(tabLayoutThemesOption.tabCount)

            viewPagerThemesOption.adapter = adapter
            viewPagerThemesOption.offscreenPageLimit = 3
//            viewPagerThemesOption.isUserInputEnabled = false
            TabLayoutMediator(tabLayoutThemesOption, viewPagerThemesOption) { tab, position ->
                tab.text = tabListThemesType[position].name
            }.attach()
        }

    }

    data class ThemeOptionsModel(val name: String)

    inner class ViewPagerAdapter(private val totalTabs: Int) : FragmentStateAdapter(this) {
        override fun createFragment(position: Int): Fragment {


            return when (position) {
                0 ->  CallScreenThemeFragment()
                1 -> CallScreenLiveThemeFragment()
                else -> CallScreenWallpaperFragment()
            }

//            return fragmentsCustomizeList[position]
        }

        override fun getItemCount(): Int {
            return totalTabs
        }

    }


}