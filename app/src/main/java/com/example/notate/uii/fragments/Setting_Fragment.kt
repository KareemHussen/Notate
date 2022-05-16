package com.example.notate.uii.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_setting.*
import android.app.Activity
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*
import android.content.Intent

import android.content.Context

import android.content.pm.PackageManager
import android.util.Log
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.example.notate.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_archived.*


class Setting_Fragment : Fragment(  R.layout.fragment_setting) {


    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val langId = restorePrefLang("lang")
        LanguageRadioGroup.check(langId)

        val themeId = restorePrefTheme("themeCode")
        ThemeRadioGroup.check(themeId)


        LanguageRadioGroup.setOnCheckedChangeListener {
                radioGroup, i ->

            if (i == arabic.id){
                Log.d("lang" , "ar")
                savePrefsInt("lang" , arabic.id)
                savePrefsLang("langCode" , "ar")
                triggerRebirth(requireContext())

            } else if (i == english.id){
                Log.d("lang" , "en")
                savePrefsInt("lang" , english.id)
                savePrefsLang("langCode" , "en")
                triggerRebirth(requireContext())


            } else if (i == langBasedOnSystem.id){
                Log.d("lang" , Locale.getDefault().language)
                savePrefsInt("lang" , langBasedOnSystem.id)
                savePrefsLang("langCode" , Locale.getDefault().language)
                triggerRebirth(requireContext())


            }
        }


        ThemeRadioGroup.setOnCheckedChangeListener { radioGroup, i ->

            if (i == light.id){
                savePrefsInt("themeCode" , light.id)
                savePrefsLang("theme" , "light")
                triggerRebirth(requireContext())

            } else if (i == dark.id){
                savePrefsInt("themeCode" , dark.id)
                savePrefsLang("theme" , "dark")
                triggerRebirth(requireContext())

            } else if (i == themeBasedOnSystem.id){
                savePrefsInt("themeCode" , themeBasedOnSystem.id)
                savePrefsLang("theme" , "system")
                triggerRebirth(requireContext())
            }
        }


        se_MenuIcon.setOnClickListener {
            this.activity!!.drawer_layout.openDrawer(Gravity.START)
        }

    }




    fun triggerRebirth(context: Context) {
        val packageManager: PackageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    fun restorePrefLang(key : String): Int {
        val pref = context!!.getSharedPreferences("settingPrefs", AppCompatActivity.MODE_PRIVATE)
        return pref.getInt(key, langBasedOnSystem.id)
    }

    fun restorePrefTheme(key : String): Int {
        val pref = context!!.getSharedPreferences("settingPrefs", AppCompatActivity.MODE_PRIVATE)
        return pref.getInt(key, themeBasedOnSystem.id)
    }

    private fun savePrefsInt(key : String, value : Int) {
        val pref = context!!.getSharedPreferences(
            "settingPrefs",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    private fun savePrefsLang(key : String, value : String) {
        val pref = context!!.getSharedPreferences(
            "settingPrefs",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putString(key, value)
        editor.commit()
    }
}