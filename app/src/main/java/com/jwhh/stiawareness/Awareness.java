package com.jwhh.stiawareness;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jwhh.stiawareness.databinding.ActivityAwarenessBinding;

public class Awareness extends AppCompatActivity {

    private ActivityAwarenessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAwarenessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Button counsellingButton = findViewById(R.id.counselling_button);

        counsellingButton.setOnClickListener(v -> {
            Intent intent = new Intent(Awareness.this, AvailableDoctors.class);

            startActivity(intent);

        });


    }
}