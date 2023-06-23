package com.nikos_group.advancednotes;

import static com.nikos_group.advancednotes.MainActivity.authController;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.nikos_group.advancednotes.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
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

        binding.signUp.setOnClickListener(v -> {
            String email = binding.email.getText().toString();
            String password = binding.password.getText().toString();
            authController.signUp(email, password, task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(this, MainActivity.class),
                            ActivityOptions.makeCustomAnimation(this, R.anim.appear, R.anim.disappear).toBundle());
                    finish();
                } else {
                    Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.signIn.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class),
                    ActivityOptions.makeCustomAnimation(this, R.anim.appear, R.anim.disappear).toBundle());
            finish();
        });
    }
}