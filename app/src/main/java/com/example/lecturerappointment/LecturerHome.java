package com.example.lecturerappointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class LecturerHome extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ArrayList<GridData> gridDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_home);
        gridDataArrayList = new ArrayList<>();

        gridDataArrayList.add(new GridData("Add course", R.drawable.ic_baseline_add_24));
        gridDataArrayList.add(new GridData("View slots",R.drawable.ic_view_solts));
        gridDataArrayList.add(new GridData("Chat",R.drawable.ic_baseline_chat));
        gridDataArrayList.add(new GridData("Notification",R.drawable.ic_baseline_info_24));
        RecyclerViewAdapterLecturerHome adapterLecturerHome = new RecyclerViewAdapterLecturerHome(gridDataArrayList,LecturerHome.this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2 );

        recyclerView = findViewById(R.id.recycler_lecturer_home);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterLecturerHome);


    }
}