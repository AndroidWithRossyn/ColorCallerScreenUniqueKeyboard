package com.uprisingscallscreen.theme.flashscreen.fragments
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pesonal.adsdk.AppManage
import com.uprisingscallscreen.theme.flashscreen.MainActivity
import com.uprisingscallscreen.theme.flashscreen.TestKeyboardActivity
import com.uprisingscallscreen.theme.flashscreen.adapter.AllThemesAdapter
import com.uprisingscallscreen.theme.flashscreen.adapter.WrapStaggeredGridLayoutManager
import com.uprisingscallscreen.theme.flashscreen.apputils.ConstValues
import com.uprisingscallscreen.theme.flashscreen.apputils.ConstValues.fullScreenAdShowCount
import com.uprisingscallscreen.theme.flashscreen.databinding.FragmentNeonBinding
import com.uprisingscallscreen.theme.flashscreen.keyboardutils.editPrefs
import com.uprisingscallscreen.theme.flashscreen.keyboardutils.getMyPreferences
import com.uprisingscallscreen.theme.flashscreen.models.ThemesModel
import keyboard.neon.newboard.util.checkIfImeIsEnabled
import keyboard.neon.newboard.util.checkIfImeIsSelected
import timber.log.Timber

class NeonThemesFragment : BaseFragment() {

    private lateinit var myActivity: MainActivity
    private var neonThemeAdCount = 0
    //    private var adLoadingBox: AdLoader? = null
    private lateinit var neonFragmentBinding: FragmentNeonBinding
    private val neonAdapter = AllThemesAdapter { themes ->
        neonAdapterClick(themes)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myActivity = context as MainActivity
    }


    private fun neonAdapterClick(
        theme: ThemesModel
    ) {

        if (checkIfImeIsEnabled(myActivity)) {

            if (checkIfImeIsSelected(myActivity)) {


//        val setTheme = "assets:ime/theme/${theme.themeName}.json"
                val setTheme = "assets:ime/theme/${theme.themeJsonName}.json"
                keyboardPrefs.theme.dayThemeRef = setTheme


                val oldFavThemeId =
                    myActivity.getMyPreferences().getInt(ConstValues.prefKeyFavThemeItem, -10)
                themesViewModel.handleFavSelection?.invoke(oldFavThemeId, theme.themeId)

                Timber.d("simple theme  old fav pref  value : $oldFavThemeId")

                themesViewModel.toggleNeonThemeStatus(theme.themeId, true)


                val themeId = myActivity.getMyPreferences().getInt(ConstValues.prefKeyThemeItem, 0)
                themesViewModel.unSelectTheme?.invoke(themeId)




                myActivity.getMyPreferences().editPrefs {
                    putInt(ConstValues.prefKeyNeonItem, theme.themeId)
                    putInt(ConstValues.prefKeyFavThemeItem, theme.themeId)
                    putInt(ConstValues.prefKeyThemeItem, 0)
                }


                if (neonThemeAdCount == fullScreenAdShowCount) {
                    neonThemeAdCount = 0
                    showFullScreenAdThemeClick(theme)
                } else {
                    neonThemeAdCount++
                    startActivity(
                        Intent(myActivity, TestKeyboardActivity::class.java).putExtra(
                            "themeModel",
                            theme
                        )
                    )

                }


            } else {
                showEnableKbBottomSheet(
                    myActivity, "Select the  Keyboard as default  and \n" +
                            " Apply Themes."
                )
            }


        } else {
            showEnableKbBottomSheet(
                myActivity, "Enable the  Keyboard  and \n" +
                        " Apply Themes. "
            )
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        neonFragmentBinding = FragmentNeonBinding.inflate(inflater, container, false)
        AppManage.getInstance(requireActivity())?.showNativeBanner(neonFragmentBinding.adsView0)
//        adLoadingBox = AdLoader(myActivity)
        neonFragmentBinding.neonThemesRv.apply {

//            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            layoutManager = WrapStaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            adapter = neonAdapter
//            addItemDecoration(GridSpacingItemDecoration(2, 15, false))
            setHasFixedSize(true)
        }

        themesViewModel.keyBoardNeonThemesListLiveData.observe(viewLifecycleOwner) {
            neonAdapter.submitList(it)
            val selectItem = myActivity.getMyPreferences().getInt(ConstValues.prefKeyNeonItem, 0)
            themesViewModel.toggleNeonThemeStatus(selectItem, true)

        }


//        loadNativeAdInList()

//        loadNativeAdInList()

        themesViewModel.unSelectNeonTheme = {

            themesViewModel.toggleNeonThemeStatus(it, false)
        }


        themesViewModel.changeFavTheme = { themeId ->


            Timber.d("change Neon theme $themeId")
//            themesViewModel.toggleThemeStatus(themeId, true)

        }



        return neonFragmentBinding.root
    }


//    private fun loadNativeAdInList() {
//        myActivity.loadAndShowAdList({
//
//            it.let { nativeAd ->
//                neonAdapter.sendNativeAd(nativeAd)
//                themesViewModel.insertAdsNeonTheme()
//            }
//
//        }, myActivity.getString(R.string.listNativeAdId))
//    }


    private fun showFullScreenAdThemeClick(theme: ThemesModel) {
        if (isInternetConnected()) {

//            fullScreenAd.loadInterstitialThemeAd(loaded = {
//
//
//                when (it) {
//                    true -> {
//
//                        adLoadingBox?.closeDialog()
//                        fullScreenAd.showThemesInterstitial(myActivity) {

            startActivity(
                Intent(myActivity, TestKeyboardActivity::class.java).putExtra(
                    "themeModel",
                    theme
                )
            )


//                        }
//
//                    }
//                    else -> {
//                        adLoadingBox?.closeDialog()
//                        startActivity(
//                            Intent(myActivity, TestKeyboardActivity::class.java).putExtra(
//                                "themeModel",
//                                theme
//                            )
//                        )
//
//
//                    }
//                }
//
//
//            }, showLoader = {
//                adLoadingBox?.showDialog()
//
//            })

        } else {
            startActivity(
                Intent(myActivity, TestKeyboardActivity::class.java).putExtra(
                    "themeModel",
                    theme
                )
            )
        }

    }
}