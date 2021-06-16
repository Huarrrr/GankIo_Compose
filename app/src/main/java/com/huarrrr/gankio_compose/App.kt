package com.huarrrr.gankio_compose

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


/**
 * App
 *
 * @author Huarrrr on 2021/6/15
 */

class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}