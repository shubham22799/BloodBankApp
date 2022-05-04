package com.example.bloodbankapp.global

import android.app.Application
import android.util.Log
import com.example.bloodbankapp.utility.SharedPreferencesManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.v("App", "Working")
        SharedPreferencesManager.createPreference(this)
    }


}