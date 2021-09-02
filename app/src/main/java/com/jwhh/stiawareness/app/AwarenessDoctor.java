package com.jwhh.stiawareness.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jwhh.stiawareness.R;
import com.jwhh.stiawareness.databinding.ActivityAwarenessDoctorBinding;

public class AwarenessDoctor extends AppCompatActivity {

    private ActivityAwarenessDoctorBinding binding;
    private Button unregister, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAwarenessDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        unregister = findViewById(R.id.unregister);
        update = findViewById(R.id.update_doctor);

        unregister.setOnClickListener(v -> {
            Intent intent = getIntent();
            String spaceName = intent.getStringExtra("strName");
            Toast.makeText(AwarenessDoctor.this, spaceName , Toast.LENGTH_LONG).show();
        });



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}