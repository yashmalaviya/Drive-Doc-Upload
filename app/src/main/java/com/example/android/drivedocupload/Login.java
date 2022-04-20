package com.example.android.drivedocupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button signUpIfNotAcc, loginBtn;
    TextInputLayout username, password;

    FirebaseDatabase database;
    DatabaseReference ref;
    String pass;
    String mfullName, mContactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //To make the activity go full screen and hiding the notif bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        signUpIfNotAcc = (Button) findViewById(R.id.signUpIfNotAcc);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        //Login Btn onClickListener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = username.getEditText().getText().toString();
                String enteredPass = password.getEditText().getText().toString();

                database = FirebaseDatabase.getInstance();
                ref = database.getReference("users");

                //Reading Data from Firebase Realtime Database
                Query  isUserValid = ref.orderByChild("username").equalTo(userId);
                isUserValid.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            Toast.makeText(Login.this, "No user with specified username! Try Signing Up", Toast.LENGTH_LONG).show();
                        }
                        else{
                            ref.child(userId).child("pass").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(Login.this, "Error getting Data!!!", Toast.LENGTH_LONG).show();
                                        Log.e("Firebase", "Error getting data! Try Again", task.getException());
                                    }
                                    else{
                                        pass = String.valueOf(task.getResult().getValue());
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                    }
                                }
                            });

                            //Reading Full Name of the User
                            ref.child(userId).child("fullName").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(Login.this, "Error getting Data!!!", Toast.LENGTH_LONG).show();
                                        Log.e("Firebase", "Error getting data! Try Again", task.getException());
                                    }
                                    else{
                                        String name = String.valueOf(task.getResult().getValue());
                                        mfullName = name;
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                    }
                                }
                            });

                            //Reading Contact of the User
                            ref.child(userId).child("phone").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(Login.this, "Error getting Data!!!", Toast.LENGTH_LONG).show();
                                        Log.e("Firebase", "Error getting data! Try Again", task.getException());
                                    }
                                    else{
                                        String phone = String.valueOf(task.getResult().getValue());
                                        mContactNumber = phone;
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                    }
                                }
                            });

                            if(enteredPass.equals(pass)){
                                Intent intentToHome = new Intent(Login.this, Home.class);
                                intentToHome.putExtra("username", userId);
                                intentToHome.putExtra("fullName", mfullName);
                                intentToHome.putExtra("contactNumber", mContactNumber);
                                startActivity(intentToHome);
                            }
                            else{
                                Toast.makeText(Login.this, "Wrong Password! Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });
        signUpIfNotAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
            }
        });
    }
}