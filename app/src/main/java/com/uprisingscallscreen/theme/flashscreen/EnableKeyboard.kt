package com.uprisingscallscreen.theme.flashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.pesonal.adsdk.AppManage
import com.uprisingscallscreen.theme.flashscreen.apputils.ConstValues
import com.uprisingscallscreen.theme.flashscreen.databinding.EnableKeyboardBinding
import keyboard.neon.newboard.InputMethodChangedReceiver
import keyboard.neon.newboard.util.checkIfImeIsEnabled
import keyboard.neon.newboard.util.checkIfImeIsSelected

class EnableKeyboard : BaseActivity(), View.OnClickListener {


    companion object {
        private const val MSG_POLLING_IME_SETTINGS = 0
        private const val IME_SETTINGS_POLLING_INTERVAL: Long = 200
    }

    var userStep = 1

    private val enableKeyboardBinding: EnableKeyboardBinding by lazy {
        EnableKeyboardBinding.inflate(layoutInflater)
    }

    private val imeChangedReceiver = InputMethodChangedReceiver {
        updateUiState {

            if (intent.getBooleanExtra(ConstValues.fromSplash, false))
                startWithClearTop(MainActivity::class.java)
            else
                finish()

        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUiState(onExecute: (() -> Unit?)? = null) {


        if (checkIfImeIsSelected(this)) {
            onExecute?.invoke()
            userStep = 3
            enableKeyboardBinding.enableKeyboardBtn.text = "Switch Keyboard"
            enableKeyboardBinding.settingsTv.text = getString(R.string.checkSettingsStepThree)
            enableKeyboardBinding.titleKeyboardTv.text = "Congratulation!"


        } else {
            userStep = 2
            enableKeyboardBinding.enableKeyboardBtn.text = "Switch Keyboard"
            enableKeyboardBinding.settingsTv.text = getString(R.string.checkSettingsStepTwo)
            enableKeyboardBinding.titleKeyboardTv.text = "Switch to Neon Keyboard"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enableKeyboardBinding.root)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        val background: Drawable = resources.getDrawable(R.drawable.status_gradient)
        window.setBackgroundDrawable(background)
        AppManage.getInstance(this@EnableKeyboard).showNative(
            findViewById<View>(R.id.adsView0) as ViewGroup,
            AppManage.ADMOB_N[0], AppManage.FACEBOOK_N[0]
        )
        enableKeyboardBinding.backBtn.setOnClickListener {
           onBackPressed()
        }
        setSupportActionBar(enableKeyboardBinding.toolbar)
        enableKeyboardBinding.enableKeyboardBtn.setOnClickListener(this)
        registerReceiver(
            imeChangedReceiver,
            IntentFilter(Intent.ACTION_INPUT_METHOD_CHANGED)
        )

    }

    private fun startWithClearTop(targetClass: Class<*>, imeiChanged: Boolean = false) {
        val intent = Intent(this, targetClass)
        intent.flags = (Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                or Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(ConstValues.fromSplash, true)
        startActivity(intent)
        if (!imeiChanged)
            finish()
    }

    override fun onResume() {
        super.onResume()
        if (!checkIfImeIsEnabled(this)) {
            userStep = 1

            with(enableKeyboardBinding)
            {
                enableKeyboardBinding.enableKeyboardBtn.text = "Enable Keyboard"
                enableKeyboardBinding.titleKeyboardTv.text = "Enable Neon Keyboard"
            }

        } else
            updateUiState()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(imeChangedReceiver)
    }

    override fun onClick(viewId: View?) {
        when (viewId?.id) {
            R.id.enableKeyboardBtn -> {
                when (userStep) {
                    1 -> {
                        SettingsPoolingHandler().startPollingImeSettings()

                        Intent().apply {
                            action = Settings.ACTION_INPUT_METHOD_SETTINGS
                            addCategory(Intent.CATEGORY_DEFAULT)
                        }.also { startActivity(it) }

                    }
                    else -> {
                        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker()
                    }
                }
            }
        }
    }


    @SuppressLint("HandlerLeak")
    private inner class SettingsPoolingHandler : Handler(
        Looper.myLooper()!!
    ) {
        override fun handleMessage(msg: Message) {
            if (msg.what == MSG_POLLING_IME_SETTINGS) {

                if (checkIfImeIsEnabled(this@EnableKeyboard)) {
                    startWithClearTop(EnableKeyboard::class.java, true)
                    updateUiState()
                    removeCallbacksAndMessages(null)
                    return
                }
                startPollingImeSettings()
            }
        }

        fun startPollingImeSettings() {
            sendMessageDelayed(
                obtainMessage(MSG_POLLING_IME_SETTINGS),
                IME_SETTINGS_POLLING_INTERVAL
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }




}