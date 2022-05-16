package com.example.notate.uii

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import com.example.notate.R
import com.example.notate.uii.fragments.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*
import java.util.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale(this,restoreLangPref("langCode"))
        setTheme()
        setContentView(R.layout.activity_splash_screen)


        val anim = AnimationUtils.loadAnimation(this, R.anim.name_anim)


        Handler().postDelayed({
            appTitle.visibility = View.VISIBLE
            appTitle.startAnimation(anim)

        },500)


        Handler().postDelayed({
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)

    }


    fun setLocale(activity: Activity, languageCode: String?) {
        Log.d("ahhh" , languageCode!!)
        if (languageCode == "ar"){
            this.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else if (languageCode == "en") {
            this.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        } else {
            this.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LOCALE
        }

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources: Resources = activity.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    fun setTheme(){
        var theme = restoreThemePref("theme")

        if (theme == "light"){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else if (theme == "dark"){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun restoreLangPref(key : String): String? {
        val pref = this.getSharedPreferences("settingPrefs", MODE_PRIVATE)
        return pref.getString(key, "en")
    }

    fun restoreThemePref(key : String): String? {
        val pref = this.getSharedPreferences("settingPrefs", MODE_PRIVATE)
        return pref.getString(key, "system")
    }
}