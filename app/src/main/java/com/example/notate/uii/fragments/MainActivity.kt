package com.example.notate.uii.fragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import com.example.notate.R

class MainActivity : AppCompatActivity() {

    private lateinit var navControler : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupMenuWithNavigationComponent()


    }

    private fun setupMenuWithNavigationComponent(){
        navControler = Navigation.findNavController(this , R.id.fragmentContainerView)
        NavigationUI.setupWithNavController(navigationView , navControler)
    }








}