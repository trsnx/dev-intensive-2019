package ru.skillbranch.devintensive

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.repositories.PreferencesRepository

class App : Application() {
    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("M_App", "onCreate app")
        PreferencesRepository.getAppTheme().also {
            AppCompatDelegate.setDefaultNightMode(it)
        }
    }
}