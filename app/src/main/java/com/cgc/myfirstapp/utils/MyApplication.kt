package com.cgc.myfirstapp.utils

import android.app.Application
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
       // FirebaseApp.initializeApp(this)
       // FacebookSdk.sdkInitialize(this)

    }

}
