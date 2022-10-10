package com.example.lecturerappointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Consult extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private RecyclerViewAdapterTimeTable timeTableRecylcerViewAdapter;
    private RecyclerView timeTableRecylerView;
    private ArrayList<ConsultationData> timeTableDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.courses_registered, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // add onlick lister to the course drowp down/ get the drop down elements from database
        timeTableRecylerView = findViewById(R.id.timetable_recycler);

        timeTableDataList= new ArrayList<>();
        addTimeTableData();
        timeTableRecylcerViewAdapter = new RecyclerViewAdapterTimeTable(timeTableDataList,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,7);
        timeTableRecylerView.setLayoutManager(layoutManager);

        timeTableRecylerView.setAdapter(timeTableRecylcerViewAdapter);

    }

    /**
     * add timetable information
     * On the days: MONDAY- SATURDAY, we may have to add 0,1 to show that slot is available or not
     * On the adapter class, view probably set the invisible the views
     */
    public void addTimeTableData()
    {
        timeTableDataList.add(0,new ConsultationData("Slots"));
        timeTableDataList.add(1,new ConsultationData("8-10"));
        timeTableDataList.add(2,new ConsultationData("10-12"));
        timeTableDataList.add(3,new ConsultationData("12-2"));
        timeTableDataList.add(4,new ConsultationData("2-4"));
        timeTableDataList.add(5,new ConsultationData("4-6"));
        timeTableDataList.add(6,new ConsultationData("6-8"));


        timeTableDataList.add(7,new ConsultationData("Mon"));

        // Monday
        timeTableDataList.add(8,new ConsultationData("0"));
        timeTableDataList.add(9,new ConsultationData("1"));
        timeTableDataList.add(10,new ConsultationData("0"));
        timeTableDataList.add(11,new ConsultationData("0"));
        timeTableDataList.add(12,new ConsultationData("0"));
        timeTableDataList.add(13,new ConsultationData("1"));

        timeTableDataList.add(14,new ConsultationData("Tue"));

        // Tuesday
        timeTableDataList.add(15,new ConsultationData("0"));
        timeTableDataList.add(16,new ConsultationData("0"));
        timeTableDataList.add(17,new ConsultationData("0"));
        timeTableDataList.add(18,new ConsultationData("1"));
        timeTableDataList.add(19,new ConsultationData("1"));
        timeTableDataList.add(20,new ConsultationData("0"));

        // Wednesday
        timeTableDataList.add(21,new ConsultationData("Wed"));
        timeTableDataList.add(22,new ConsultationData("0"));
        timeTableDataList.add(23,new ConsultationData("0"));
        timeTableDataList.add(24,new ConsultationData("0"));
        timeTableDataList.add(25,new ConsultationData("0"));
        timeTableDataList.add(26,new ConsultationData("1"));
        timeTableDataList.add(27,new ConsultationData("0"));

        // Thusrday
        timeTableDataList.add(28,new ConsultationData("Thu"));

        timeTableDataList.add(29,new ConsultationData("0"));
        timeTableDataList.add(30,new ConsultationData("1"));
        timeTableDataList.add(31,new ConsultationData("1"));
        timeTableDataList.add(32,new ConsultationData("0"));
        timeTableDataList.add(33,new ConsultationData("0"));
        timeTableDataList.add(34,new ConsultationData("0"));

        // Friday

        timeTableDataList.add(35,new ConsultationData("Fri"));
        timeTableDataList.add(36,new ConsultationData("0"));
        timeTableDataList.add(37,new ConsultationData("0"));
        timeTableDataList.add(38,new ConsultationData("0"));
        timeTableDataList.add(39,new ConsultationData("1"));
        timeTableDataList.add(40,new ConsultationData("1"));
        timeTableDataList.add(41,new ConsultationData("0"));

        // Saturday
        timeTableDataList.add(42,new ConsultationData("Sat"));
        timeTableDataList.add(43,new ConsultationData("0"));
        timeTableDataList.add(44,new ConsultationData("0"));
        timeTableDataList.add(45,new ConsultationData("0"));
        timeTableDataList.add(46,new ConsultationData("0"));
        timeTableDataList.add(47,new ConsultationData("0"));
        timeTableDataList.add(48,new ConsultationData("0"));

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
    {
        String mystr = (String)adapterView.getItemAtPosition(position);
        Toast.makeText(this,mystr,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}