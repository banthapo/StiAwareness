package com.jwhh.stiawareness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jwhh.stiawareness.databinding.ActivityLogInBinding;


public class LogIn extends AppCompatActivity {


    private ActivityLogInBinding binding;

    private Button logIn;
    private View view;
    private TextView signUp;
    private EditText spaceName, password;
    private final DatabaseManager databaseManager = new DatabaseManager(LogIn.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        logIn = findViewById(R.id.login_button);
        signUp = findViewById(R.id.sign_up_text);


        logIn.setOnClickListener(v -> {
                spaceName = findViewById(R.id.login_name);
                password = findViewById(R.id.login_password);

                String getName = spaceName.getText().toString();
                String getPassword = password.getText().toString();

                boolean checkLogin = databaseManager.checkLogDetails(getName, getPassword);

                if (checkLogin == true){

                    Intent intent = new Intent(LogIn.this, Awareness.class);
//                    String members = databaseManager.getMemberDetails().toString();
                    Toast.makeText(LogIn.this, "Log in successful" , Toast.LENGTH_LONG).show();

                    startActivity(intent);
                }else {
                    Toast.makeText(LogIn.this, "Log in failed", Toast.LENGTH_LONG).show();
                }


        });

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LogIn.this, SignUp.class);

            startActivity(intent);
        });

    }
}