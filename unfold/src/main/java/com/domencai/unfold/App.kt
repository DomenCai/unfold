package com.domencai.unfold

import android.app.Application
import jackmego.com.jieba_android.JiebaSegmenter

/**
 * Created by DomenCai on 2020-02-18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        JiebaSegmenter.init(this)
    }

}