package com.uprisingscallscreen.theme.flashscreen

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.pesonal.adsdk.AppManage
import com.uprisingscallscreen.theme.flashscreen.callertheme.ExitActivity
import com.uprisingscallscreen.theme.flashscreen.callertheme.FavouriteActivity
import com.uprisingscallscreen.theme.flashscreen.callertheme.dialer.DialerActivity
import com.uprisingscallscreen.theme.flashscreen.databinding.ActivityMainBinding
import com.uprisingscallscreen.theme.flashscreen.fragments.HomeFragment
import com.uprisingscallscreen.theme.flashscreen.fragments.IconFragment
import com.uprisingscallscreen.theme.flashscreen.fragments.SettingsMainFragment
import com.uprisingscallscreen.theme.flashscreen.fragments.ThemeOptionsFragment


class MainActivity : BaseActivity() {
    companion object {
        private const val SELECTED_ITEM_ID = "SELECTED_ITEM_ID"
        val DEFAULT_DIALER_REQUEST_ID = 83
        val TAG = "MADARA"
    }

    private val mainActivityBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityBinding.root)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        val background: Drawable = resources.getDrawable(R.drawable.status_gradient)
        window.setBackgroundDrawable(background)
        AppManage.getInstance(this@MainActivity).loadInterstitialAd(
            this,
            AppManage.ADMOB_I[0], AppManage.FACEBOOK_I[0]
        )
        with(mainActivityBinding) {
            bottomNavigationView.itemIconTintList = null
            bottomNavigationView.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.themesNav ->

                        loadFragment(HomeFragment())


                    R.id.keyboardNav ->
                        AppManage.getInstance(this@MainActivity).showInterstitialAd(this@MainActivity,
                                {
                                    loadFragment(ThemeOptionsFragment())
                                }, "", AppManage.app_mainClickCntSwAd
                            )

                    R.id.iconsNav ->
                        AppManage.getInstance(this@MainActivity)
                            .showInterstitialAd(
                                this@MainActivity,
                                {
                                    loadFragment(IconFragment())
                                }, "", AppManage.app_mainClickCntSwAd
                            )


                    R.id.settingsNav ->
                        AppManage.getInstance(this@MainActivity)
                            .showInterstitialAd(
                                this@MainActivity,
                                {
                                    loadFragment(SettingsMainFragment())
                                }, "", AppManage.app_mainClickCntSwAd
                            )


                }
                true
            }
        }

        mainActivityBinding.menuBtn.setOnClickListener {
            mainActivityBinding.drawer.openDrawer(Gravity.LEFT)
        }
        mainActivityBinding.callerScreenLl.setOnClickListener {

            mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
            loadFragmentWithIcon(HomeFragment(), R.drawable.home_clear)


        }

        mainActivityBinding.keyboardLl.setOnClickListener {
            AppManage.getInstance(this@MainActivity).showInterstitialAd(this@MainActivity,
                {
                    mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                    loadFragmentWithIcon(ThemeOptionsFragment(), R.drawable.keyboard_clear)
                }, AppManage.ADMOB, AppManage.app_mainClickCntSwAd
            )


        }

        mainActivityBinding.iconLl.setOnClickListener {
            AppManage.getInstance(this@MainActivity).showInterstitialAd(this@MainActivity,
                {
                    mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                    loadFragmentWithIcon(IconFragment(), R.drawable.icons_clear)
                }, AppManage.ADMOB, AppManage.app_mainClickCntSwAd
            )


        }


        mainActivityBinding.settingLl.setOnClickListener {
            AppManage.getInstance(this@MainActivity).showInterstitialAd(this@MainActivity,
                {
                    mainActivityBinding.drawer.closeDrawer(Gravity.LEFT)
                    loadFragmentWithIcon(SettingsMainFragment(), R.drawable.setting_clear)
                }, AppManage.ADMOB, AppManage.app_mainClickCntSwAd
            )

        }
        mainActivityBinding.bottomNavigationView.selectedItemId =
            savedInstanceState?.getInt(SELECTED_ITEM_ID) ?: R.id.themesNav

        mainActivityBinding.dialerBtn.setOnClickListener {
            AppManage.getInstance(this@MainActivity).showInterstitialAd(this@MainActivity,
                {
                    startActivity(Intent(this@MainActivity, DialerActivity::class.java))
                }, AppManage.ADMOB, AppManage.app_mainClickCntSwAd
            )
        }

        mainActivityBinding.favouriteBtn.setOnClickListener {
            AppManage.getInstance(this@MainActivity).showInterstitialAd(this@MainActivity,
                {
                    startActivity(Intent(this@MainActivity, FavouriteActivity::class.java))
                }, AppManage.ADMOB, AppManage.app_mainClickCntSwAd
            )
        }


        mainActivityBinding.bottomNavigationView.setOnItemReselectedListener {
            if (it.itemId != mainActivityBinding.bottomNavigationView.selectedItemId) {
                mainActivityBinding.bottomNavigationView.selectedItemId = it.itemId
            }
        }
    }

    private fun loadFragmentWithIcon(fragment: Fragment, iconResourceId: Int) {
        val bundle = Bundle()
        bundle.putInt("iconResourceId", iconResourceId)
        fragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()

        // Update the selected item in the bottom navigation view
        when (fragment) {
            is HomeFragment -> mainActivityBinding.bottomNavigationView.selectedItemId =
                R.id.themesNav
            is ThemeOptionsFragment -> mainActivityBinding.bottomNavigationView.selectedItemId =
                R.id.keyboardNav
            is IconFragment -> mainActivityBinding.bottomNavigationView.selectedItemId =
                R.id.iconsNav
            is SettingsMainFragment -> mainActivityBinding.bottomNavigationView.selectedItemId =
                R.id.settingsNav
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onBackPressed() {
        startActivity(Intent(this@MainActivity, ExitActivity::class.java))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_ITEM_ID, mainActivityBinding.bottomNavigationView.selectedItemId)
    }

    override fun onResume() {
        super.onResume()
    }


}



