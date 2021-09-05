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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.jwhh.stiawareness.R;
import com.jwhh.stiawareness.database.DatabaseManager;
import com.jwhh.stiawareness.databinding.ActivityAwarenessDoctorBinding;

public class AwarenessDoctor extends AppCompatActivity {

    private ActivityAwarenessDoctorBinding binding;
    private Button unregister, update, updateDoctor;
    private int phoneNumber;
    private Intent intent;
    private String spaceName, doctorName, docTitle, docFirstname, docSurname, docEmail;
    private TextView docName;
    private EditText title, firstName, surname, email;
    private PopupWindow popupWindow = null;
    private DisplayMetrics displayMetrics ;

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

        update.setOnClickListener(v -> {
            showPopup();
        });

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

        binding.fab.setOnClickListener(view ->
                Snackbar.make(view, "you have: 0 messages", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

    }

    private void showPopup() {
        displayMetrics = getResources().getDisplayMetrics();
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.update_layout, null);

        updateDoctor = (Button) layout.findViewById(R.id.update_button);
        title = (EditText) layout.findViewById(R.id.update_title);
        firstName = (EditText) layout.findViewById(R.id.update_firstname);
        surname = (EditText) layout.findViewById(R.id.update_surname);
        email = (EditText) layout.findViewById(R.id.update_email);

        popupWindow = new PopupWindow();
        popupWindow.setContentView(layout);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(null);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 2, 0);

      updateDoctor.setOnClickListener(v -> {
          docTitle = title.getText().toString();
          docFirstname = firstName.getText().toString();
          docSurname = surname.getText().toString();
          docEmail = email.getText().toString();

          phoneNumber = databaseManager.getPhoneNumber(spaceName);

          try {
              String doctor = docTitle + " " + docFirstname + " " + docSurname;
              databaseManager.updateDoctor(docTitle, docFirstname, doctorName, docEmail, phoneNumber, doctor);
              String name = databaseManager.getDoctorName(phoneNumber);

              Toast.makeText(AwarenessDoctor.this," Successfully updated your details" + name, Toast.LENGTH_LONG).show();
          } catch (Exception e){
              Toast.makeText(AwarenessDoctor.this, "update failed!", Toast.LENGTH_LONG).show();
          }


      });

        Toast.makeText(AwarenessDoctor.this, "successful" + doctorName, Toast.LENGTH_SHORT).show();
    }
}