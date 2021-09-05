package com.jwhh.stiawareness.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jwhh.stiawareness.R;
import com.jwhh.stiawareness.database.DatabaseManager;
import com.jwhh.stiawareness.databinding.ActivityAwarenessDoctorBinding;
import com.jwhh.stiawareness.models.DoctorModel;

public class AwarenessDoctor extends AppCompatActivity {

    //declaring field variables
    private ActivityAwarenessDoctorBinding binding;
    private Button unregister, update, updateDoctor, cancelUpdate;
    private int phoneNumber;
    private Intent intent;
    private String spaceName, doctorName, docTitle, docFirstname, docSurname, docEmail, docPhoneNumber;
    private EditText title, firstName, surname, email, phoneNum;
    private PopupWindow popupWindow = null;
    private DisplayMetrics displayMetrics ;
    private DoctorModel doctorModel;
    private boolean success;

    private DatabaseManager databaseManager = new DatabaseManager(AwarenessDoctor.this);
    private LayoutInflater inflater;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAwarenessDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        unregister = findViewById(R.id.unregister);
        update = findViewById(R.id.update_doctor);

        intent = getIntent();
        spaceName = intent.getStringExtra("strName");
        phoneNumber = databaseManager.getPhoneNumber(spaceName);
        doctorName = databaseManager.getDoctorName(phoneNumber);

        //setting the update button onClick action
        update.setOnClickListener(v -> {
            showPopup();
        });

        unregisterDoctor();

        binding.fab.setOnClickListener(view ->
                Snackbar.make(view, "you have: 0 messages", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

    }

    //deleting doctor account
    private void unregisterDoctor() {
        unregister.setOnClickListener(v -> {
            intent = new Intent(AwarenessDoctor.this, LogIn.class);

            try {
                databaseManager.deleteDoctor(phoneNumber);
                databaseManager.deleteMember(phoneNumber);
                Toast.makeText(AwarenessDoctor.this, "successfully unregistered " + spaceName, Toast.LENGTH_LONG).show();
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(AwarenessDoctor.this, "failed to unregister", Toast.LENGTH_LONG).show();
            }


        });
    }

    //implementing a popup view for doctor update
    private void showPopup() {
        displayMetrics = getResources().getDisplayMetrics();
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.update_layout, null);

        updateDoctor = (Button) layout.findViewById(R.id.update_button);
        cancelUpdate = (Button) layout.findViewById(R.id.cancel_update);
        title = (EditText) layout.findViewById(R.id.update_title);
        firstName = (EditText) layout.findViewById(R.id.update_firstname);
        surname = (EditText) layout.findViewById(R.id.update_surname);
        email = (EditText) layout.findViewById(R.id.update_email);
        phoneNum = (EditText) layout.findViewById(R.id.update_phone_number);

        popupWindow = new PopupWindow();
        popupWindow.setContentView(layout);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(null);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 2, 0);

        updateDoctorDetails();

        cancelUpdate.setOnClickListener(v ->{
            popupWindow.dismiss();
        });

    }

    //doing doctor detail update
    private void updateDoctorDetails() {
        updateDoctor.setOnClickListener(v -> {

            docTitle = title.getText().toString();
            docFirstname = firstName.getText().toString();
            docSurname = surname.getText().toString();
            docEmail = email.getText().toString();
            docPhoneNumber = phoneNum.getText().toString();

            phoneNumber = databaseManager.getPhoneNumber(spaceName);
            try {
                successCases(databaseManager, doctorModel);
                return;

            } catch (Exception e){
                Toast.makeText(AwarenessDoctor.this, "update failed!", Toast.LENGTH_LONG).show();
            }


        });
    }
    private void successCases(DatabaseManager database, DoctorModel doctorModel) {

        String getTitle = title.getText().toString();
        String getFirstname = firstName.getText().toString();
        String getSurname = surname.getText().toString();
        String getEmail = email.getText().toString();
        String getPhoneNumber = phoneNum.getText().toString();

        boolean titleLength = title.length() < 6;
        boolean firstNameLength = firstName.length() < 20;
        boolean surnameLength = surname.length() < 20;
        boolean phoneNumberLength = phoneNum.length() < 3;
        boolean checkEmailValidity = getEmail.contains("@");
        boolean checkEmail = database.checkDoctorEmail(getEmail);

        String name = databaseManager.getDoctorName(phoneNumber);

        if (titleLength && !getTitle.isEmpty()) {
            success = true;
        } else {
            Toast.makeText(AwarenessDoctor.this, "Please enter a valid title!", Toast.LENGTH_LONG).show();
            return;
        }

        if (firstNameLength && !getFirstname.isEmpty()) {
            success = true;
        } else {
            Toast.makeText(AwarenessDoctor.this, "Please enter a valid name!", Toast.LENGTH_LONG).show();
            return;
        }

        if (surnameLength && !getSurname.isEmpty()) {
            success = true;
        } else {
            Toast.makeText(AwarenessDoctor.this, "Please enter a valid surname!", Toast.LENGTH_LONG).show();
            return;
        }

        if (checkEmailValidity) {
            success = true;
        } else {
            Toast.makeText(AwarenessDoctor.this, "Please enter a valid email", Toast.LENGTH_LONG).show();
            success = false;
            return;
        }
        if (checkEmail) {
            success = true;
        } else {
            Toast.makeText(AwarenessDoctor.this, "Email Address already in use", Toast.LENGTH_LONG).show();
            return;
        }

        if (!phoneNumberLength && !getPhoneNumber.isEmpty()) {
            success = true;
            boolean checkPhoneNumber = database.checkPhoneNumber(Integer.parseInt(getPhoneNumber));

            if (checkPhoneNumber) {
                success = true;
            } else {
                Toast.makeText(AwarenessDoctor.this, "Phone number already in use", Toast.LENGTH_LONG).show();
                return;
            }
        } else {
            Toast.makeText(AwarenessDoctor.this, "Please enter a valid phone number!", Toast.LENGTH_LONG).show();
            return;
        }

        if (success) {
                String doctor = docTitle + " " + docFirstname + " " + docSurname;
                databaseManager.updateDoctor(docTitle, docFirstname, doctorName, docEmail, doctor, phoneNumber, Integer.parseInt(docPhoneNumber));
                databaseManager.setMemberPhoneNumber(phoneNumber, Integer.parseInt(docPhoneNumber));

                Toast.makeText(AwarenessDoctor.this, " Successfully updated details from: " + name + "\nto: " +
                        doctor, Toast.LENGTH_LONG).show();
                popupWindow.dismiss();
        }
    }

}