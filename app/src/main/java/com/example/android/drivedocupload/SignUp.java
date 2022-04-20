package com.example.android.drivedocupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class SignUp extends AppCompatActivity {

    TextInputLayout fullNameId, usernameId, emailId, phoneId, passId;
    Button signUpBtn, backToLogin;
    ProgressBar signUpProgressBar;

    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Making the screen go fullscreen and hide the notif bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        fullNameId = findViewById(R.id.fullNameId);
        usernameId = findViewById(R.id.usernameId);
        emailId = findViewById(R.id.emailId);
        phoneId = findViewById(R.id.phoneId);
        passId = findViewById(R.id.passId);
        signUpBtn = findViewById(R.id.signUpBtn);
        backToLogin = findViewById(R.id.backToLogin);
        signUpProgressBar = (ProgressBar) findViewById(R.id.signUpProgressBar);
        mAuth = FirebaseAuth.getInstance();

        //Signing Up and writing user data to Firebase Realtime Database
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = fullNameId.getEditText().getText().toString();
                String username = usernameId.getEditText().getText().toString();
                String email = emailId.getEditText().getText().toString();
                String phone = phoneId.getEditText().getText().toString();
                String pass = passId.getEditText().getText().toString();

                //Checking if the phone number is valid
                if(phone.trim().length() != 10){
                    Toast.makeText(SignUp.this, "Enter Correct Contact Number!", Toast.LENGTH_SHORT).show();
                }
                else{
                    signUpProgressBar.setVisibility(View.VISIBLE);
                    signUpBtn.setVisibility(View.GONE);

                    mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            signUpProgressBar.setVisibility(View.GONE);
                            signUpBtn.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken){
                            signUpProgressBar.setVisibility(View.GONE);
                            signUpBtn.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(SignUp.this, OTPVerification.class);
                            intent.putExtra("mobileNumber", phone);
                            intent.putExtra("OTPCode", otp);
                            intent.putExtra("fullName", fullName);
                            intent.putExtra("username", username);
                            intent.putExtra("email", email);
                            intent.putExtra("pass", pass);
                            if(intent.resolveActivity(getPackageManager()) != null){
                                startActivity(intent);
                            }
                        }
                    };

                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+91" + phone)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(SignUp.this)
                            .setCallbacks(mCallbacks)
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }
        });

        //Going Back to Login Page
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
            }
        });
    }
}