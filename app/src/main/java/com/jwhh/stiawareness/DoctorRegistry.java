package com.jwhh.stiawareness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jwhh.stiawareness.databinding.ActivityDoctorRegistryBinding;

public class DoctorRegistry extends AppCompatActivity {

    private ActivityDoctorRegistryBinding binding;

    private EditText title;
    private EditText fName;
    private EditText sName;
    private EditText tNumber;
    private EditText emailAddress;
    private String name;
    private DoctorModel doctorModel;
    private DoctorNameModel doctorNameModel;

    private ImageView backButton;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoctorRegistryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        register = findViewById(R.id.register_button);
        backButton = findViewById(R.id.from_doctor_registry);

        doctorRegistration(register);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DoctorRegistry.this, LogIn.class);
            startActivity(intent);
        });
    }

    private void doctorRegistration(Button register) {
        title = findViewById(R.id.doctor_title);
        fName = findViewById(R.id.first_name);
        sName = findViewById(R.id.doctor_surname);
        tNumber = findViewById(R.id.doctor_telephone_number);
        emailAddress = findViewById(R.id.doctor_email);

        register.setOnClickListener(v -> {

            Intent intent = new Intent(DoctorRegistry.this, Awareness.class);
            DatabaseManager doctorDatabase = new DatabaseManager(DoctorRegistry.this);

            name = title.getText().toString()+ " " +fName.getText().toString()+ " " +sName.getText().toString();

            try {
                doctorModel = new DoctorModel(title.getText().toString(), fName.getText().toString(), sName.getText().toString()
                        , Integer.parseInt(tNumber.getText().toString()), emailAddress.getText().toString(), name);


                boolean success =  doctorDatabase.addDoctor(doctorModel);

                if (success) {
                    Toast.makeText(DoctorRegistry.this, "Successfully registered", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else{
                    Toast.makeText(DoctorRegistry.this, "Registration failed", Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){
                Toast.makeText(DoctorRegistry.this, "Fill all fields please ", Toast.LENGTH_LONG).show();
                doctorModel = new DoctorModel(null, null, null, 0, null, null);
            }


        });
    }

}