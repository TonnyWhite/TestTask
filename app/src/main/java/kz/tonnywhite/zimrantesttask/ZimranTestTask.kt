package kz.tonnywhite.zimrantesttask

import android.app.Application

class ZimranTestTask : Application() {
    override fun onCreate() {
        super.onCreate()
        initPlatformSDK()
    }
}

fun ZimranTestTask.initPlatformSDK() =
    PlatformSDK.init(
        configuration = applicationContext
    )