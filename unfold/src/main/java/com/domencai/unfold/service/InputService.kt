package com.domencai.unfold.service

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import com.domencai.unfold.utils.IntentUtil
import com.domencai.unfold.widget.InputView
import jackmego.com.jieba_android.JiebaSegmenter
import kotlinx.coroutines.*
import java.util.regex.Pattern
import kotlin.math.roundToInt

/**
 * Created by DomenCai on 2020-02-16.
 */
class InputService : InputMethodService() {
    private val inputView by lazy {
        InputView(applicationContext).apply {
            inputCallback = { input ->
                GlobalScope.launch {
                    val words = withContext(Dispatchers.IO) {
                        JiebaSegmenter.getJiebaSegmenterSingleton().getDividedString(input)
                    }
                    val blankChar = arrayOf("", "\u2060", "\u2061", "\u2062", "\u2063", "\u034F")
                    val smallChar = arrayOf("\u05B9", "\u05C4")
                    val pattern = Pattern.compile("[\u4e00-\u9fa5]")
                    for (word in words) {
                        val random = Math.random()
                        delay((random * 100).toLong())

                        if (!pattern.matcher(word).matches()) {
                            currentInputConnection.commitText(word, 0)
                            continue
                        }

                        val index: Int = (random * 8).roundToInt()
                        val text = if (index < blankChar.size) "${word}${blankChar[index]}" else word
                        currentInputConnection.commitText(text, 0)

                        if (random > 0.95) {
                            currentInputConnection.commitText(smallChar[index % 2], 0)
                        }
                    }
                }
            }

            changeCallback = {
                IntentUtil.showInputMethodPicker(applicationContext)
            }
        }
    }

    override fun onCreateInputView(): View {
        return inputView
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        inputView.reset()
    }
}