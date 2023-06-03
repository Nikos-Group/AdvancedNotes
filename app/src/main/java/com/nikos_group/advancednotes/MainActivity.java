package com.nikos_group.advancednotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nikos_group.advancednotes.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}