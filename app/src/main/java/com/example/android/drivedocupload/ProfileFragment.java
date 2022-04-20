package com.example.android.drivedocupload;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    TextInputEditText fullNameET, contactNumberET;
    TextView mfullName, musername;

    Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Bundle bundle = this.getArguments();
        String username = bundle.getString("username");
        String fullName = bundle.getString("fullName");
        String contactNumber = bundle.getString("contactNumber");

        fullNameET = view.findViewById(R.id.fullNameET);
        contactNumberET = view.findViewById(R.id.contactNumberET);
        mfullName = view.findViewById(R.id.fullName);
        musername = view.findViewById(R.id.username);
        logout = view.findViewById(R.id.logoutBtn);

        fullNameET.setText(fullName);
        contactNumberET.setText(contactNumber);
        mfullName.setText(fullName);
        musername.setText(username);

        //Loggin Out
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Login.class);
                startActivity(i);
            }
        });
        return view;
    }
}