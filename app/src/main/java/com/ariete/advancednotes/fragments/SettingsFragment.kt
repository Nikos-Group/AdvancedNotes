package com.ariete.advancednotes.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ariete.advancednotes.R
import com.ariete.advancednotes.activities.MainActivity
import com.ariete.advancednotes.databinding.FragmentSettingsBinding
import com.ariete.advancednotes.databinding.LayoutAboutUsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsFragment :
    Fragment(R.layout.fragment_settings),
    View.OnClickListener {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mView: View

    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var aboutUs: CardView

    private lateinit var dialogAboutUs: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(
            inflater,
            container,
            false
        )

        animation()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mView = view

        assign_bottomNavigation_item()

        aboutUs = binding.aboutUs
        setting_up_click_aboutUs()
        setting_up_dialogAboutUs()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun animation() {
        lifecycleScope.launch {
            binding.root.alpha = 0f

            delay(500)

            binding.root
                .animate()
                .alpha(1f)
                .duration = 250
        }
    }

    private fun `assign_bottomNavigation_item`() {
        bottomNavigation = (activity as MainActivity).bottomNavigation
        bottomNavigation
            .menu
            .findItem(R.id.settings)
            .isChecked = true
    }

    private fun `setting_up_click_aboutUs`() {
        aboutUs.setOnClickListener(this)
    }

    private fun `setting_up_dialogAboutUs`() {
        val builder = AlertDialog.Builder(context)

        val view = LayoutAboutUsBinding.inflate(
            LayoutInflater.from(context)
        )

        builder.setView(view.root)
        dialogAboutUs = builder.create()

        dialogAboutUs.window!!.setBackgroundDrawableResource(
            R.drawable.background_dialog_about_us
        )

        view.ok.setOnClickListener {
            dialogAboutUs.dismiss()
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.aboutUs -> {
                dialogAboutUs.show()
            }
        }
    }
}