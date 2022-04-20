package com.example.android.drivedocupload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.drivedocupload.databinding.ActivityHomeBinding;
import com.example.android.drivedocupload.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    ActivityHomeBinding binding;
    BottomNavigationView bottomNavigationView;

    Button uploadButton;

    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();


    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_home);

        uploadButton = (Button) findViewById(R.id.uploadBtn);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");

        String userName = getIntent().getStringExtra("username");
        String fullName = getIntent().getStringExtra("fullName");
        String contactNumber = getIntent().getStringExtra("contactNumber");
        //username.setText(userName);

        //Bringing Data to Profile Tab


        Bundle bundle = new Bundle();
        bundle.putString("username", userName);
        bundle.putString("fullName", fullName);
        bundle.putString("contactNumber", contactNumber);
        profileFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }
}