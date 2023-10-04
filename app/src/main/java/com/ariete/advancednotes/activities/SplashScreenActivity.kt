package com.ariete.advancednotes.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.ariete.advancednotes.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        /**
            * layoutInflater instantiates a layout XML file.
            * ---------------------------------------------
            * layoutInflater создает XML файл макета.
        */

        setContentView(binding.root)

        lifecycleScope.launch {
            delay(700)

            val intent = Intent(
                baseContext,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }

        /**
            * This code block uses the `lifecycleScope.launch` function
            * to launch a coroutine
            * within an Activity lifecycle scope.
            *
            * Inside the `launch` block,
            * the coroutine suspends its execution for 700 milliseconds using
            * the `delay` function.
            *
            * After a delay
            * there is a transition between SplashScreenActivity and MainActivity.
            * --------------------------------------------------------------------
            * Этот блок кода использует функцию lifecycleScope.launch()
            * для запуска корутины
            * в рамках жизненного цикла активности.
            *
            * Внутри блока launch корутина приостанавливает
            * свое выполнение на 700 миллисекунд,
            * используя функцию delay().
            *
            * После задержки,
            * происходит переход из SplashScreenActivity в MainActivity.
        */
    }
}