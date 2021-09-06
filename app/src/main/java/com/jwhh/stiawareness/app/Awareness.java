package com.jwhh.stiawareness.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jwhh.stiawareness.R;
import com.jwhh.stiawareness.database.DatabaseManager;
import com.jwhh.stiawareness.databinding.ActivityAwarenessBinding;

public class Awareness extends AppCompatActivity implements Runnable {
    private ActivityAwarenessBinding binding;

    //declaring field variables
    private ImageView backButton;
    private Button counsellingButton;
    private TextView deleteAccount;
    private Intent intent;
    private DatabaseManager databaseManager = new DatabaseManager(Awareness.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //running class objects on a thread
        run();
    }

    //implementing runnable
    @Override
    public void run() {
        binding = ActivityAwarenessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        counsellingButton = findViewById(R.id.counselling_button);
        backButton = findViewById(R.id.from_awareness);
        deleteAccount = findViewById(R.id.delete_account);

        counsellingButtonFunc();

        deleteMemberAccount();

        returnButtonFunc();
    }

    //setting action for onClick listener to counselling button
    private void counsellingButtonFunc() {
        counsellingButton.setOnClickListener(v -> {
            intent = new Intent(Awareness.this, AvailableDoctors.class);

            startActivity(intent);
        });
    }

    //setting action for onClick listener to return icon
    private void returnButtonFunc() {
        backButton.setOnClickListener(v -> {
            intent = new Intent(Awareness.this, LogIn.class);
            startActivity(intent);
        });
    }

    //removing account from database using phone number and spacename and intent extra
    private void deleteMemberAccount() {
        deleteAccount.setOnClickListener(v -> {
            intent = getIntent();
            String spaceName = intent.getStringExtra("spaceName");
            intent = new Intent(Awareness.this, LogIn.class);

            int phoneNumber = databaseManager.getPhoneNumber(spaceName);
            databaseManager.deleteMember(phoneNumber);
            Toast.makeText(Awareness.this, "successfully unregistered " + spaceName, Toast.LENGTH_LONG).show();
            startActivity(intent);
        });
    }
}











