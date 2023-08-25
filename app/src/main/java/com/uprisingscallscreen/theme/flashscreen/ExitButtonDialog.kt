package com.uprisingscallscreen.theme.flashscreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uprisingscallscreen.theme.flashscreen.databinding.ExitButtonLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExitButtonDialog : BottomSheetDialogFragment() {


    private lateinit var exitBottomSheetDialog: ExitButtonLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        exitBottomSheetDialog = ExitButtonLayoutBinding.inflate(inflater, container, false)
        return exitBottomSheetDialog.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        exitBottomSheetDialog.rateUsBtn.setOnClickListener {
            val appPackageName = BuildConfig.APPLICATION_ID
            try {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (e: ActivityNotFoundException) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }


        }
        exitBottomSheetDialog.yesBtn.setOnClickListener {
            activity?.let {
                if (!it.isFinishing) {
                    dismiss()
                    (it as MainActivity).finish()

                }
            }
        }
        exitBottomSheetDialog.noBtn.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): ExitButtonDialog {
            return ExitButtonDialog()
        }
    }
}