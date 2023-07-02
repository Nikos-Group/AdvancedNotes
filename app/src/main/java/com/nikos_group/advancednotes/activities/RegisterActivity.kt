package com.nikos_group.advancednotes.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.nikos_group.advancednotes.R
import com.nikos_group.advancednotes.databinding.ActivityRegisterBinding
import com.nikos_group.advancednotes.helper.toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Objects

class RegisterActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            transaction()
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

    override fun onClick(v: View?) {
        when (v) {
            binding.signUp -> {
                val mail = binding.email.text.toString()

                val password = binding.password.text.toString()

                MainActivity.authController.signUp(
                    mail,
                    password,
                    OnCompleteListener<AuthResult> {
                        if (it.isSuccessful) {
                            startActivity(
                                Intent(
                                    this,
                                    MainActivity::class.java
                                ),

                                ActivityOptions.makeCustomAnimation(
                                    this,
                                    R.anim.appear,
                                    R.anim.disappear
                                ).toBundle()
                            )

                            finish()
                        } else {
                            this.toast(
                                it.exception?.message ?: "No exception"
                            )
                        }
                    })
            }

            binding.signIn -> {
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
    }
}