package com.ariete.advancednotes.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.ariete.advancednotes.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ariete.advancednotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var bottomNavigation: BottomNavigationView

    private lateinit var currentFragment: FragmentContainerView

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentFragment = binding.fragment
        bottomNavigation = binding.bottomNavigation
    }

    override fun onStart() {
        super.onStart()

        navController = currentFragment.findNavController()
        setting_bottom_naviagtion()
    }

    private fun `setting_bottom_naviagtion`() {
        bottomNavigation.itemIconTintList = null

        bottomNavigation.setOnItemSelectedListener {

            val homeFragmentContext = findViewById<View>(R.id.fragment_home)?.context
            val noteFragmentContext = findViewById<View>(R.id.fragment_note)?.context
            val settingsFragmentContext = findViewById<View>(R.id.fragment_settings)?.context


            when(it.itemId) {
                R.id.notes -> switching_to_homeFragment(
                    settingsFragmentContext,
                    navController
                )

                R.id.settings -> switching_to_settingsFragment(
                    homeFragmentContext,
                    noteFragmentContext,
                    navController
                )
            }

            true
            /**
             * Return true to indicate the item (operation) is selected (applied).
             * -------------------------------------------------------------------
             * Возврщаем true, чтобы указать что элемент (операция) выбран (применена).
             */
        }
    }

    private fun `switching_to_homeFragment`(
        settingsFragmentContext: Context?,
        navController: NavController
    ) {
        settingsFragmentContext?.let { context ->
            if (currentFragment.context == context) {
                navController.navigate(
                    R.id.action_settingsFragment_to_homeFragment,
                )
            }
        }
    }

    private fun `switching_to_settingsFragment`(
        homeFragmentContext: Context?,
        noteFragmentContext: Context?,
        navController: NavController
    ) {
        homeFragmentContext?.let { context ->
            if (currentFragment.context == context) {
                navController.navigate(
                    R.id.action_homeFragment_to_settingsFragment
                )
            }
        }

        noteFragmentContext?.let { context ->
            if (currentFragment.context == context) {
                navController.navigate(
                    R.id.action_noteFragment_to_settingsFragment
                )
            }
        }
    }
}