package com.uprisingscallscreen.theme.flashscreen.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pesonal.adsdk.AppManage
import com.uprisingscallscreen.theme.flashscreen.EnableKeyboard
import com.uprisingscallscreen.theme.flashscreen.R
import com.uprisingscallscreen.theme.flashscreen.viewmodel.ThemesViewModel
import keyboard.neon.newboard.ime.core.Preferences
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class BaseFragment : Fragment() {
    val FRAGMENT_TAG = "FRAGMENT_TAG"


    protected val themesViewModel: ThemesViewModel by sharedViewModel()

    //    private var fragNativeAd: NativeAd? = null
//    protected val fullScreenAd: AdsManager by inject()
    protected val keyboardPrefs = Preferences.default()

    protected fun loadChildFragment(fragment: Fragment, frameId: Int) {
        childFragmentManager
            .beginTransaction()
            .replace(frameId, fragment, FRAGMENT_TAG)
            .commit()
    }

    protected fun showEnableKbBottomSheet(context: Context, titleText: String = "") {


        val modalSheetView =
            layoutInflater.inflate(R.layout.action_bottomsheet_enablekeyboard, null)
        val dialog = BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme)
        dialog.let {

            it.setContentView(modalSheetView)
            it.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            it.show()
        }


        if (titleText.isNotBlank()) {
            dialog.findViewById<TextView>(R.id.deleteTextTvBs)?.text = titleText
        }


        dialog.findViewById<TextView>(R.id.cancelBtnEk)?.setOnClickListener {

            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.enableKeyboardButtonEk)?.setOnClickListener {
            AppManage.getInstance(context).showInterstitialAd(
                context,
                {
                    startActivity(
                        Intent(context, EnableKeyboard::class.java)
                    )
                    dialog.dismiss()
                }, AppManage.ADMOB, AppManage.app_mainClickCntSwAd
            )


        }

        dialog.show()

    }


    protected fun isInternetConnected(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }


        return false
    }


}