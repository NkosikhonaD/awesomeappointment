package com.example.lecturerappointment;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FirebaseDatabaseHelper
{
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private List<SlotClass> slotsList = new ArrayList<>();

    public interface  DataStatus
    {
        void DataIsLoaded(List<SlotClass> slots, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();


    }

    public FirebaseDatabaseHelper()
    {
        database = FirebaseDatabase.getInstance();
        databaseReference= database.getReference("slots");

    }
    public void  readSLots(final DataStatus dataStatus)
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slotsList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : snapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    SlotClass slot = keyNode.getValue(SlotClass.class);
                    slotsList.add(slot);
                }
                dataStatus.DataIsLoaded(slotsList,keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
