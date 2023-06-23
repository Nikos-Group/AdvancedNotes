package com.nikos_group.advancednotes;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.nikos_group.advancednotes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    public static AuthController authController = new AuthController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AdvancedNotes);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!authController.isAuth()) {
            startActivity(new Intent(this, LoginActivity.class),
                    ActivityOptions.makeCustomAnimation(this, R.anim.appear, R.anim.disappear).toBundle());
            finish();
        }

        new Thread(() -> {
            try {
                binding.getRoot().setAlpha(0f);
                Thread.sleep(500);
                runOnUiThread(() -> {
                    binding.getRoot().animate().alpha(1f).setDuration(250);
                });
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        binding.welcome.setOnClickListener(v -> {
            authController.signOut();
            startActivity(new Intent(this, LoginActivity.class),
                    ActivityOptions.makeCustomAnimation(this, R.anim.appear, R.anim.disappear).toBundle());
            finish();
        });
    }
}