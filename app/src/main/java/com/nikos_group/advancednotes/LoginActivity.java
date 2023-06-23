package com.nikos_group.advancednotes;

import static com.nikos_group.advancednotes.MainActivity.authController;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nikos_group.advancednotes.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.signIn.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            authController.signIn(email, password, task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(this, MainActivity.class),
                            ActivityOptions.makeCustomAnimation(this, R.anim.appear, R.anim.disappear).toBundle());
                    finish();
                } else {
                    Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.signUp.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class),
                    ActivityOptions.makeCustomAnimation(this, R.anim.appear, R.anim.disappear).toBundle());
            finish();
        });
    }
}