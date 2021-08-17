package com.jwhh.stiawareness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jwhh.stiawareness.databinding.ActivitySignUpBinding;

public class SignUp extends AppCompatActivity {


    private ActivitySignUpBinding binding;

    private MemberModel memberModel;

    private EditText spaceName;
    private EditText phoneNumber;
    private EditText password;
    private EditText confirmPassword;
    private boolean success;
    private ImageView backButton;
    private Button doctorSignUp;
    private Button memberSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        doctorSignUp = findViewById(R.id.sign_up_as_doctor);
        memberSignUp = findViewById(R.id.sign_up_as_member);
        backButton = findViewById(R.id.from_sign_in);

        spaceName = findViewById(R.id.space_name);
        phoneNumber = findViewById(R.id.phone_number);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.password_confirm);

        doctorSignUp.setOnClickListener(v -> {
            Intent doctorIntent = new Intent(SignUp.this, DoctorRegistry.class);
            memberSignIn();
            if (success){
                startActivity(doctorIntent);
            }
        });

        memberSignUp.setOnClickListener(v -> {
            Intent memberIntent = new Intent(SignUp.this, LogIn.class);
            memberSignIn();

            if( success) {
                startActivity(memberIntent);
            }

        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignUp.this, LogIn.class);
            startActivity(intent);
        });

    }

    private void memberSignIn() {
        DatabaseManager memberDatabase = new DatabaseManager(SignUp.this);

        try {
            memberModel = new MemberModel(spaceName.getText().toString(), Integer.parseInt(phoneNumber.getText().toString())
                    , password.getText().toString(), confirmPassword.getText().toString());

            successCases (memberDatabase, memberModel);

        }catch (Exception e){
            Toast.makeText(SignUp.this, "please fill all fields ", Toast.LENGTH_LONG).show();
            memberModel = new MemberModel(null, 0, null,  null);
        }

    }

    private void successCases(DatabaseManager memberDatabase, MemberModel memberModel) {

        boolean spaceNameLength = spaceName.length() < 10;
        boolean phoneNumberLength = phoneNumber.length()  < 3;
        boolean passwordLength = password.length() < 3 ;

        String cPassword = memberModel.getPassword();
        String cPassword2 = memberModel.getConfirmPassword();
        boolean confirmPassword = cPassword == cPassword2;

        success = false;

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
//
//        if (confirmPassword){
//            success = true ;
//        } else {
//            Toast.makeText(SignUpAsMember.this, "passwords do not match" + cPassword + cPassword2, Toast.LENGTH_LONG).show();
//            return;
//        }

        if (success){
            memberDatabase.addMember(memberModel);
            Toast.makeText(SignUp.this, "successfully signed in", Toast.LENGTH_LONG).show();
        }
    }
}