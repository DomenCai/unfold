package com.domencai.unfold.utils

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.inputmethod.InputMethodManager

/**
 * Created by DomenCai on 2020-02-17.
 */
object IntentUtil {

    fun toEnableInput(context: Context) {
        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun showInputMethodPicker(context: Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showInputMethodPicker()
    }

    fun isInputEnabled(context: Context): Boolean {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val methodList = inputManager.enabledInputMethodList
        for (info in methodList) {
            if (info.packageName == context.packageName) {
                return true
            }
        }
        return false
    }

    fun isInputSelected(context: Context): Boolean {
        try {
            val currentInputMethod = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.DEFAULT_INPUT_METHOD
            )
            return currentInputMethod.startsWith(context.packageName)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }
}