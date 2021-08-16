package com.jwhh.stiawareness;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jwhh.stiawareness.databinding.ActivityLogInBinding;

public class LogIn extends AppCompatActivity {


    private ActivityLogInBinding binding;

    private Button logIn;
    private View view;
    private TextView signUp;
    private EditText spaceName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        spaceName = findViewById(R.id.login_name);
        password = findViewById(R.id.login_name);

        logIn = findViewById(R.id.login_button);
        signUp = findViewById(R.id.sign_up_text);

        logIn.setOnClickListener(v -> {

            try {
                String getName = spaceName.getText().toString();
                Editable getPassword = password.getText();

                Intent intent = new Intent(LogIn.this, Awareness.class);

                startActivity(intent);

            } catch (Exception e){
                Snackbar.make(view, "Log in failed", Snackbar.LENGTH_LONG);
            }

        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LogIn.this, SignUp.class);

            startActivity(intent);
        });

    }
}