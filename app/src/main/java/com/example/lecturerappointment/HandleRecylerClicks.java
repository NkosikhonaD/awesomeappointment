package com.example.lecturerappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HandleRecylerClicks extends AppCompatActivity {

    private FirebaseAuth logged = FirebaseAuth.getInstance();
    private FirebaseUser userLogged = logged.getCurrentUser();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference slotsDatabaseReference =database.getReference("slots");
    private DatabaseReference slotSlices = database.getReference("Slot_slices");
    private DatabaseReference courses = database.getReference("courses");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_recyler_clicks);

    }
}