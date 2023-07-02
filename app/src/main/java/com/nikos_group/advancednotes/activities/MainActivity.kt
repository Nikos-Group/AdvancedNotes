package com.nikos_group.advancednotes.activities

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.nikos_group.advancednotes.db.NoteDatabase
import com.nikos_group.advancednotes.repository.NoteRepository
import com.nikos_group.advancednotes.viewmodel.NoteViewModel
import com.nikos_group.advancednotes.viewmodel.NoteViewModelProviderFactory
import com.nikos_group.advancednotes.R
import com.nikos_group.advancednotes.authcontrollers.AuthController
import com.nikos_group.advancednotes.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var noteViewModel: NoteViewModel

    companion object {
        val authController: AuthController = AuthController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()

        authentication()

        lifecycleScope.launch {
            transaction()
        }
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(
            NoteDatabase(this)
        )

        val viewModelProviderFactory = NoteViewModelProviderFactory(
            application,
            noteRepository
        )

        noteViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        )[NoteViewModel::class.java]
    }

    private fun authentication() {
        if (!authController.isAuth) {
            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                ),

                ActivityOptions.makeCustomAnimation(
                    this,
                    R.anim.appear,
                    R.anim.disappear
                ).toBundle()
            )

            finish()
        }
    }

    private suspend fun transaction() {
        binding.root.alpha = 0f
        delay(500)
        binding.root
            .animate()
            .alpha(1f)
            .duration = 250
    }
}