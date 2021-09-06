package com.jwhh.stiawareness.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jwhh.stiawareness.R;
import com.jwhh.stiawareness.database.DatabaseManager;
import com.jwhh.stiawareness.databinding.ActivityDoctorRegistryBinding;
import com.jwhh.stiawareness.models.DoctorModel;

public class DoctorRegistry extends AppCompatActivity implements Runnable {
    private ActivityDoctorRegistryBinding binding;

    //declaring field variables
    private boolean success;
    private EditText title, fName, sName, emailAddress;
    private int tNumber;
    private String name;
    private DoctorModel doctorModel;

    private ImageView backButton;
    private Button register;

    DatabaseManager doctorDatabase = new DatabaseManager(DoctorRegistry.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //running class objects on a thread
        run();
    }

    //getting values for doctor registration
    private void doctorRegistration(Button register) {
        title = findViewById(R.id.doctor_title);
        fName = findViewById(R.id.first_name);
        sName = findViewById(R.id.doctor_surname);
        emailAddress = findViewById(R.id.doctor_email);

        registerDoctor(register);
    }

    //setting onClick action to return icon
    private void returnButton() {
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorRegistry.this, LogIn.class);
            startActivity(intent);
        });
    }

    //registering doctor using doctor model
    private boolean registerDoctor(Button register) {
        register.setOnClickListener(v -> {
            name = title.getText().toString() + " " + fName.getText().toString() + " " + sName.getText().toString();
            tNumber = SignUp.getPhoneNumber();

            try {
                doctorModel = new DoctorModel(title.getText().toString(), fName.getText().toString(), sName.getText().toString()
                        , tNumber, emailAddress.getText().toString(), name);

                successCases(doctorDatabase, doctorModel);

            } catch (Exception e) {
                Toast.makeText(DoctorRegistry.this, "Fill all fields please ", Toast.LENGTH_LONG).show();
                doctorModel = new DoctorModel(null, null, null, 0, null, null);
            }
        });
        return success;
    }

    //checking validity of information collected on member signup form
    private void successCases(DatabaseManager database, DoctorModel doctorModel) {

        String getTitle = title.getText().toString();
        String getFirstname = fName.getText().toString();
        String getSurname = sName.getText().toString();
        String getEmail = emailAddress.getText().toString();

        boolean titleLength = title.length() < 6;
        boolean firstNameLength = fName.length() < 20;
        boolean surnameLength = sName.length() < 20;
        boolean checkEmailValidity = getEmail.contains("@");

        boolean checkEmail = database.checkDoctorEmail(getEmail);

        if (titleLength && !getTitle.isEmpty()) {
            success = true;
        } else {
            Toast.makeText(DoctorRegistry.this, "Please enter a valid title!", Toast.LENGTH_LONG).show();
            return;
        }

        if (firstNameLength && !getFirstname.isEmpty()) {
            success = true;
        } else {
            Toast.makeText(DoctorRegistry.this, "Please enter a valid name!", Toast.LENGTH_LONG).show();
            return;
        }

        if (surnameLength && !getSurname.isEmpty()) {
            success = true;
        } else {
            Toast.makeText(DoctorRegistry.this, "Please enter a valid surname!", Toast.LENGTH_LONG).show();
            return;
        }
        if (checkEmailValidity) {
            success = true;
        } else {
            Toast.makeText(DoctorRegistry.this, "Please enter a valid email", Toast.LENGTH_LONG).show();
            success = false;
            return;
        }
        if (checkEmail) {
            success = true;
        } else {
            Toast.makeText(DoctorRegistry.this, "Email Address already in use", Toast.LENGTH_LONG).show();
            return;
        }

        if (success) {
            Intent intent = new Intent(DoctorRegistry.this, LogIn.class);

            database.addDoctor(doctorModel);
            Toast.makeText(DoctorRegistry.this, "Successfully registered", Toast.LENGTH_LONG).show();

            startActivity(intent);
        }
    }


    //implementing runnable
    @Override
    public void run() {
        binding = ActivityDoctorRegistryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        register = findViewById(R.id.register_button);
        backButton = findViewById(R.id.from_doctor_registry);

        doctorRegistration(register);

        returnButton();
    }

}