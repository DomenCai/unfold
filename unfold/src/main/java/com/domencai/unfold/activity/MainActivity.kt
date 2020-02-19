package com.domencai.unfold.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.domencai.unfold.R
import com.domencai.unfold.utils.IntentUtil
import kotlinx.android.synthetic.main.acitvity_main.*

class MainActivity : AppCompatActivity() {

    private val mReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                btn_to_select.isVisible = !IntentUtil.isInputSelected(this@MainActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_main)

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_INPUT_METHOD_CHANGED)
        registerReceiver(mReceiver, filter)

        activate_layout.setOnClickListener { IntentUtil.toEnableInput(this) }
        change_layout.setOnClickListener { IntentUtil.showInputMethodPicker(this) }

    }

    override fun onResume() {
        super.onResume()

        btn_to_setting.isVisible = !IntentUtil.isInputEnabled(this)
        btn_to_select.isVisible = !IntentUtil.isInputSelected(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}
