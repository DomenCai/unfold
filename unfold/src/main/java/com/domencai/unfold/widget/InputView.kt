package com.domencai.unfold.widget

import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.domencai.unfold.R
import kotlinx.android.synthetic.main.layout_input_view.view.*

/**
 * Created by DomenCai on 2020-02-17.
 */
class InputView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    var inputCallback: ((String) -> Unit)? = null
    var changeCallback: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.layout_input_view, this)

        btn_input.setOnClickListener {
            inputCallback?.invoke(tv_clip.text.toString())
        }

        btn_change.setOnClickListener {
            changeCallback?.invoke()
        }
    }

    fun reset() {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip?.apply {
            tv_clip.text = getItemAt(0).text
        }
    }

}