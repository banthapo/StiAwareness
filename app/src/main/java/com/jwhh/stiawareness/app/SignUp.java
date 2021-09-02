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
import com.jwhh.stiawareness.databinding.ActivitySignUpBinding;
import com.jwhh.stiawareness.models.MemberModel;

public class SignUp extends AppCompatActivity implements Runnable{


    private ActivitySignUpBinding binding;

    private MemberModel memberModel;

    private EditText spaceName;
    private EditText phoneNumber;
    private EditText password;
    private EditText confirmPassword;

    private static int pNumber;
    private boolean success;
    private ImageView backButton;
    private Button doctorSignUp;
    private Button memberSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        run();

    }

    private void backButton() {
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LogIn.class);

            startActivity(intent);
        });
    }

    private void memberSignUp() {
        memberSignUp.setOnClickListener(v -> {
            Intent memberIntent = new Intent(SignUp.this, LogIn.class);

            if( memberSignIn()) {
                startActivity(memberIntent);
            } else {
                return ;
            }
        });
    }

    private void doctorSignUp() {
        doctorSignUp.setOnClickListener(v -> {
            if (memberSignIn()){

                Intent doctorIntent = new Intent(SignUp.this, DoctorRegistry.class);
                startActivity(doctorIntent);
            }
        });
    }

    private boolean memberSignIn() {
        DatabaseManager memberDatabase = new DatabaseManager(SignUp.this);

        try {
            memberModel = new MemberModel(spaceName.getText().toString(), Integer.parseInt(phoneNumber.getText().toString())
                    , password.getText().toString());

            successCases (memberDatabase, memberModel);

        }catch (Exception e){
            Toast.makeText(SignUp.this, "please fill all fields ", Toast.LENGTH_LONG).show();
            memberModel = new MemberModel(null, 0, null);

        }
        return  success;
    }

    private void successCases(DatabaseManager database, MemberModel memberModel) {

        String getSpaceName = spaceName.getText().toString();
        String getPassword = password.getText().toString();
        String getPasswordConfirm = confirmPassword.getText().toString();

        boolean spaceNameLength = spaceName.length() < 10;
        boolean phoneNumberLength = phoneNumber.length()  < 3;
        boolean passwordLength = password.length() < 3 ;
        boolean checkPassword = getPassword.equals(getPasswordConfirm);

        boolean checkSpaceName = database.checkSpaceName(getSpaceName);

        if (checkSpaceName){
            success = true;
        } else {
            Toast.makeText(SignUp.this, "Spacename already exists", Toast.LENGTH_LONG).show();
            return;
        }

        if (spaceNameLength) {
            success = true;
        } else {
            Toast.makeText(SignUp.this, "name too long, try again", Toast.LENGTH_LONG).show();
            return;
        }

        if (!phoneNumberLength) {
            success = true;
        } else{
            Toast.makeText(SignUp.this, "Phone number too short", Toast.LENGTH_LONG).show();
            return;
        }

        if (!passwordLength) {
            success = true;
        } else {
            Toast.makeText(SignUp.this, "Short password", Toast.LENGTH_LONG).show();
            return;
        }
        if (checkPassword){
            success = true;
        } else {
            Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            success = false;
            return;
        }

        if (success){
            pNumber = Integer.parseInt(phoneNumber.getText().toString());
            database.addMember(memberModel);
            Toast.makeText(SignUp.this, "successfully signed in", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void run() {
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        doctorSignUp = findViewById(R.id.sign_up_as_doctor);
        memberSignUp = findViewById(R.id.sign_up_as_member);
        backButton = findViewById(R.id.from_sign_in);

        spaceName = findViewById(R.id.space_name);
        phoneNumber = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.c_password);

        doctorSignUp();

        memberSignUp();

        backButton();
    }

    public static int getPhoneNumber(){
        return pNumber;
    }
}