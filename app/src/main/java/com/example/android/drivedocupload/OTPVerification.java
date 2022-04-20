package com.example.android.drivedocupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OTPVerification extends AppCompatActivity {

    EditText inputOTP1, inputOTP2, inputOTP3, inputOTP4, inputOTP5, inputOTP6;
    String sentOTP;
    ProgressBar otpVerificationProgressBar;
    TextView submitBtn;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hooks
        otpVerificationProgressBar = (ProgressBar) findViewById(R.id.otpVerificationProgressBar);
        submitBtn = (TextView) findViewById(R.id.submitOTPBtn);
        mAuth = FirebaseAuth.getInstance();

        inputOTP1 = (EditText) findViewById(R.id.inputOtp1);
        inputOTP2 = (EditText) findViewById(R.id.inputOtp2);
        inputOTP3 = (EditText) findViewById(R.id.inputOtp3);
        inputOTP4 = (EditText) findViewById(R.id.inputOtp4);
        inputOTP5 = (EditText) findViewById(R.id.inputOtp5);
        inputOTP6 = (EditText) findViewById(R.id.inputOtp6);

        String fullName = getIntent().getStringExtra("fullName");
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String pass = getIntent().getStringExtra("pass");
        String phone = getIntent().getStringExtra("mobileNumber");

        sentOTP = getIntent().getStringExtra("OTPCode");

        TextView phoneNumber = (TextView) findViewById(R.id.phoneNumber);
        phoneNumber.setText(String.format("+91-%s", phone));

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                ref = database.getReference("users");

                if(inputOTP1.getText().toString().trim().isEmpty() || inputOTP2.getText().toString().trim().isEmpty() || inputOTP3.getText().toString().trim().isEmpty() || inputOTP4.getText().toString().trim().isEmpty() || inputOTP5.getText().toString().trim().isEmpty() || inputOTP6.getText().toString().trim().isEmpty()){
                    Toast.makeText(OTPVerification.this, "Enter All the Digits of OTP", Toast.LENGTH_SHORT).show();
                }
                else{
                    String enteredOTP = inputOTP1.getText().toString() +
                            inputOTP2.getText().toString() +
                            inputOTP3.getText().toString() +
                            inputOTP4.getText().toString() +
                            inputOTP5.getText().toString() +
                            inputOTP6.getText().toString();

                    if(sentOTP != null){
                        otpVerificationProgressBar.setVisibility(View.VISIBLE);
                        submitBtn.setVisibility(View.GONE);

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sentOTP, enteredOTP);
                        mAuth.signInWithCredential(credential).addOnCompleteListener(OTPVerification.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                otpVerificationProgressBar.setVisibility(View.GONE);
                                submitBtn.setVisibility(View.VISIBLE);

                                if(task.isSuccessful()){
                                    //Sending the user data to Users class object
                                    Users user = new Users(fullName, username, email, phone, pass);

                                    //Writing data to Firebase Realtime Database
                                    ref.child(username).setValue(user);

                                    //Toast for telling that the user data has been sent to Firebase Realtime Database
                                    Toast.makeText(OTPVerification.this, "You have been Registered. Go back and Login", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(OTPVerification.this, Login.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(OTPVerification.this, "Authentication Failed! Try Again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(OTPVerification.this, "Error! Try Again", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        nextOtp();
    }

    //Method for changing focus of cursor from one otp box to another automatically
    private void nextOtp(){
        inputOTP1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOTP2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputOTP2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOTP3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputOTP3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOTP4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputOTP4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOTP5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputOTP5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputOTP6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}