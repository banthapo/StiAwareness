package com.jwhh.stiawareness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.jwhh.stiawareness.databinding.ActivityAwarenessBinding;

public class Awareness extends AppCompatActivity implements Runnable{

    private ActivityAwarenessBinding binding;
    private ImageView backButton;
    private Button counsellingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        run();
    }


    @Override
    public void run() {
        binding = ActivityAwarenessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        counsellingButton = findViewById(R.id.counselling_button);
        backButton = findViewById(R.id.from_awareness);

        counsellingButton.setOnClickListener(v -> {
            Intent intent = new Intent(Awareness.this, AvailableDoctors.class);

            startActivity(intent);

        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(Awareness.this, LogIn.class);
            startActivity(intent);
        });
    }
}











