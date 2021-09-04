package com.jwhh.stiawareness.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jwhh.stiawareness.R;
import com.jwhh.stiawareness.database.DatabaseManager;
import com.jwhh.stiawareness.databinding.ActivityLogInBinding;

import java.util.ArrayList;


public class LogIn extends AppCompatActivity implements Runnable {


    private ActivityLogInBinding binding;

    private Button logIn;
    private View view;
    private TextView signUp;
    private EditText spaceName, password;
    private final DatabaseManager databaseManager = new DatabaseManager(LogIn.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      run();

    }

    private void signUp() {
        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LogIn.this, SignUp.class);

            startActivity(intent);
        });
    }

    private void logIn() {
        logIn.setOnClickListener(v -> {
            spaceName = findViewById(R.id.login_name);
            password = findViewById(R.id.login_password);

            String getName = spaceName.getText().toString();
            String getPassword = password.getText().toString();

            boolean checkLogin = databaseManager.checkLogDetails(getName, getPassword);
            ArrayList<String> doctorName = (ArrayList<String>) databaseManager.memberDoctors();

            try {
                if (checkLogin) {
                    for (int i = 0; doctorName.size() > i; i++) {
                        String docName = doctorName.get(i);
                        Intent intent;
                        if (docName.equals(getName)) {
                            intent = new Intent(LogIn.this, AwarenessDoctor.class);
                            intent.putExtra("strName", getName);
                            startActivity(intent);
                            return;
                        }
                    }

                    Intent i = new Intent(LogIn.this, Awareness.class);
                    i.putExtra("spaceName", getName);
                    startActivity(i);
                    Toast.makeText(LogIn.this, "Log in successful", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LogIn.this, "Log in failed", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e){
                Toast.makeText(LogIn.this, "failed", Toast.LENGTH_LONG).show();

            }

        });
    }

    @Override
    public void run() {
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        logIn = findViewById(R.id.login_button);
        signUp = findViewById(R.id.sign_up_text);


        logIn();

        signUp();

    }
}