package com.example.lecturerappointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EventListener;


public class Consult extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    private RecyclerViewAdapterTimeTable timeTableRecylcerViewAdapter;
    private RecyclerView timeTableRecylerView;
    private ArrayList<ConsultationData> timeTableDataList;
    private ArrayAdapter<CharSequence> adapter ;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<CharSequence> courseList;
    private ArrayList<CharSequence> keysList;
    private ArrayList<CharSequence> slotStartTimeList;
    private ArrayList<CharSequence> slotEndTimeList;
    private ArrayList<CharSequence> keyListSlotSlices;
    private ArrayList<CharSequence> courseListSlices;
    private ArrayList<CharSequence> slotStudentList;

    private ArrayList<CharSequence> dayList;
    private Button createSlotButton;

    private DatabaseReference databaseReference = database.getReference();
    private DatabaseReference courseDatabaseReference = database.getReference("courses");
    private DatabaseReference slotReferences = database.getReference("slots");
    private DatabaseReference slotSlicesDatabaseReference = database.getReference("Slot_slices");

    private  Spinner spinner;
    private FirebaseAuth loggedUser;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        spinner = findViewById(R.id.spinner);


        loggedUser = FirebaseAuth.getInstance();
        currentUser = loggedUser.getCurrentUser();


        courseList= new ArrayList<>();
        keysList = new ArrayList<>();

        keyListSlotSlices = new ArrayList<>();
        courseListSlices = new ArrayList<>();

       slotStartTimeList = new ArrayList<>();
       slotEndTimeList= new ArrayList<>();
       slotStudentList = new ArrayList<>();
       dayList = new ArrayList<>();
        createSlotButton = findViewById(R.id.create_slot);
        if(Character.isDigit(currentUser.getEmail().charAt(0)))
        {
            createSlotButton.setEnabled(false);
            createSlotButton.setVisibility(View.INVISIBLE);

        }
        else
        {
            createSlotButton.setEnabled(true);
            createSlotButton.setVisibility(View.VISIBLE);
        }
        createSlotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent addSlot = new Intent(Consult.this,LecturerAddSlot.class);
                startActivity(addSlot);
            }
        });

        adapter= new ArrayAdapter<>(Consult.this,android.R.layout.simple_spinner_item,courseList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);




        // add on slick lister to the course drown down / get the drop down elements from database
        //
        timeTableRecylerView = findViewById(R.id.timetable_recycler);

        timeTableDataList= new ArrayList<>();

        addTimeTableData();

        showDataCoursesSpinner();

        timeTableRecylcerViewAdapter = new RecyclerViewAdapterTimeTable(timeTableDataList,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,7);
        timeTableRecylerView.setLayoutManager(layoutManager);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
              String  course = adapterView.getItemAtPosition(position).toString();
                showDataTimeTableSlots(course,timeTableDataList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //spinner.setSelection(0);
        if(spinner.getSelectedItem()!=null)
        {
            showDataTimeTableSlots(spinner.getSelectedItem().toString(),timeTableDataList);

        }
        //timeTableRecylerView.setAdapter(timeTableRecylcerViewAdapter);




    }

    /**
     * add timetable information
     * On the days: MONDAY- SATURDAY, we may have to add 0,1 to show that slot is available or not
     * On the adapter class, view probably set the invisible the views
     */

    private void updateTimeTableData(ArrayList<ConsultationData> currentConsultationDataList, String day,String startTime,String endTime)
    {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            if(start.getHour()==8 || start.getHour()==9)
            {
                if(day.equalsIgnoreCase("mon"))
                {
                    currentConsultationDataList.set(8,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("tue"))
                {
                    currentConsultationDataList.set(15,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("wed"))
                {
                    currentConsultationDataList.set(22,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("thu"))
                {
                    currentConsultationDataList.set(29,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("fri"))
                {
                    currentConsultationDataList.set(36,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("sat"))
                {
                    currentConsultationDataList.set(43,new ConsultationData("1"));
                }

                timeTableRecylcerViewAdapter.notifyDataSetChanged();
            }
            if(start.getHour()==10 || start.getHour()==11)
            {
                if(day.equalsIgnoreCase("mon"))
                {
                    currentConsultationDataList.set(9,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("tue"))
                {
                    currentConsultationDataList.set(16,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("wed"))
                {
                    currentConsultationDataList.set(23,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("thu"))
                {
                    currentConsultationDataList.set(30,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("fri"))
                {
                    currentConsultationDataList.set(37,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("sat"))
                {
                    currentConsultationDataList.set(44,new ConsultationData("1"));
                }

                timeTableRecylcerViewAdapter.notifyDataSetChanged();
            }
            if(start.getHour()==11 || start.getHour()==12)
            {
                if(day.equalsIgnoreCase("mon"))
                {
                    currentConsultationDataList.set(10,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("tue"))
                {
                    currentConsultationDataList.set(17,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("wed"))
                {
                    currentConsultationDataList.set(24,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("thu"))
                {
                    currentConsultationDataList.set(31,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("fri"))
                {
                    currentConsultationDataList.set(38,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("sat"))
                {
                    currentConsultationDataList.set(45,new ConsultationData("1"));
                }

                timeTableRecylcerViewAdapter.notifyDataSetChanged();
            }
            if(start.getHour()==13 || start.getHour()==14)
            {
                if(day.equalsIgnoreCase("mon"))
                {
                    currentConsultationDataList.set(11,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("tue"))
                {
                    currentConsultationDataList.set(18,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("wed"))
                {
                    currentConsultationDataList.set(25,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("thu"))
                {
                    currentConsultationDataList.set(32,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("fri"))
                {
                    currentConsultationDataList.set(39,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("sat"))
                {
                    currentConsultationDataList.set(46,new ConsultationData("1"));
                }

                timeTableRecylcerViewAdapter.notifyDataSetChanged();
            }

            if(start.getHour()==15 || start.getHour()==16)
            {
                if(day.equalsIgnoreCase("mon"))
                {
                    currentConsultationDataList.set(12,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("tue"))
                {
                    currentConsultationDataList.set(19,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("wed"))
                {
                    currentConsultationDataList.set(26,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("thu"))
                {
                    currentConsultationDataList.set(33,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("fri"))
                {
                    currentConsultationDataList.set(40,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("sat"))
                {
                    currentConsultationDataList.set(47,new ConsultationData("1"));
                }

                timeTableRecylcerViewAdapter.notifyDataSetChanged();
            }
            if(start.getHour()==17 || start.getHour()==18)
            {
                if(day.equalsIgnoreCase("mon"))
                {
                    currentConsultationDataList.set(13,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("tue"))
                {
                    currentConsultationDataList.set(20,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("wed"))
                {
                    currentConsultationDataList.set(27,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("thu"))
                {
                    currentConsultationDataList.set(34,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("fri"))
                {
                    currentConsultationDataList.set(41,new ConsultationData("1"));
                }
                if(day.equalsIgnoreCase("sat"))
                {
                    currentConsultationDataList.set(48,new ConsultationData("1"));
                }

                timeTableRecylcerViewAdapter.notifyDataSetChanged();
            }

        }
    }

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
        timeTableDataList.add(8,new ConsultationData("0")); // 8 - 9
        timeTableDataList.add(9,new ConsultationData("0")); // 10 - 11
        timeTableDataList.add(10,new ConsultationData("0")); // 11 - 12
        timeTableDataList.add(11,new ConsultationData("0")); // 13 - 14
        timeTableDataList.add(12,new ConsultationData("0")); // 14 - 15
        timeTableDataList.add(13,new ConsultationData("0")); // 16 - 17

        timeTableDataList.add(14,new ConsultationData("Tue"));

        // Tuesday
        timeTableDataList.add(15,new ConsultationData("0"));
        timeTableDataList.add(16,new ConsultationData("0"));
        timeTableDataList.add(17,new ConsultationData("0"));
        timeTableDataList.add(18,new ConsultationData("0"));
        timeTableDataList.add(19,new ConsultationData("0"));
        timeTableDataList.add(20,new ConsultationData("0"));

        // Wednesday
        timeTableDataList.add(21,new ConsultationData("Wed"));
        timeTableDataList.add(22,new ConsultationData("0"));
        timeTableDataList.add(23,new ConsultationData("0"));
        timeTableDataList.add(24,new ConsultationData("0"));
        timeTableDataList.add(25,new ConsultationData("0"));
        timeTableDataList.add(26,new ConsultationData("0"));
        timeTableDataList.add(27,new ConsultationData("0"));

        // Thusrday
        timeTableDataList.add(28,new ConsultationData("Thu"));

        timeTableDataList.add(29,new ConsultationData("0"));
        timeTableDataList.add(30,new ConsultationData("0"));
        timeTableDataList.add(31,new ConsultationData("0"));
        timeTableDataList.add(32,new ConsultationData("0"));
        timeTableDataList.add(33,new ConsultationData("0"));
        timeTableDataList.add(34,new ConsultationData("0"));

        // Friday

        timeTableDataList.add(35,new ConsultationData("Fri"));
        timeTableDataList.add(36,new ConsultationData("0"));
        timeTableDataList.add(37,new ConsultationData("0"));
        timeTableDataList.add(38,new ConsultationData("0"));
        timeTableDataList.add(39,new ConsultationData("0"));
        timeTableDataList.add(40,new ConsultationData("0"));
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

    private void showDataTimeTableSlots(String courseCode,ArrayList<ConsultationData>  currentconsultationDataArrayList)
    {
        timeTableDataList.set(0,new ConsultationData("Slots"));
        timeTableDataList.set(1,new ConsultationData("8-10"));
        timeTableDataList.set(2,new ConsultationData("10-12"));
        timeTableDataList.set(3,new ConsultationData("12-2"));
        timeTableDataList.set(4,new ConsultationData("2-4"));
        timeTableDataList.set(5,new ConsultationData("4-6"));
        timeTableDataList.set(6,new ConsultationData("6-8"));
        String currentEmail = currentUser.getEmail();
        keyListSlotSlices.clear();
        courseListSlices.clear();
        slotEndTimeList.clear();
        slotStartTimeList.clear();
        slotStudentList.clear();
        slotReferences.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    SlotClass slot = dataSnapshot.getValue(SlotClass.class);
                    if(currentEmail.equals(slot.getLecturer()) && courseCode.equals(slot.getCourse())) {
                        courseListSlices.add(slot.getCourse());
                        keyListSlotSlices.add(dataSnapshot.getKey());
                        //slotStudentList.add(slotSlice.getStudent());
                        slotEndTimeList.add(slot.getEndtime());
                        slotStartTimeList.add(slot.getStarttime());
                        updateTimeTableData(currentconsultationDataArrayList,slot.getDay(),slot.getStarttime(),slot.getEndtime());
                    }
                    else
                    {
                        if(courseCode.equals(slot.getCourse()))
                        {
                            courseListSlices.add(slot.getCourse());
                            keyListSlotSlices.add(dataSnapshot.getKey());
                            //slotStudentList.add(slotSlice.getStudent());
                            slotEndTimeList.add(slot.getEndtime());
                            slotStartTimeList.add(slot.getStarttime());
                            updateTimeTableData(currentconsultationDataArrayList,slot.getDay(),slot.getStarttime(),slot.getEndtime());
                        }

                    }
                }

                timeTableRecylcerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        timeTableRecylerView.setAdapter(timeTableRecylcerViewAdapter);
    }

    public void showDataTimeTableSlices(String courseCode)
    {
        String currentEmail = currentUser.getEmail();
        keyListSlotSlices.clear();
        courseListSlices.clear();

        slotEndTimeList.clear();
        slotStartTimeList.clear();
        slotStudentList.clear();
        slotSlicesDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    SlotSlice slotSlice = dataSnapshot.getValue(SlotSlice.class);
                    if(currentEmail.equals(slotSlice.getLecturer()) && courseCode.equals(slotSlice.getCourse())) {
                        courseListSlices.add(slotSlice.getCourse());
                        keyListSlotSlices.add(dataSnapshot.getKey());
                        slotStudentList.add(slotSlice.getStudent());
                        slotEndTimeList.add(slotSlice.getEndtime());
                        slotStartTimeList.add(slotSlice.getStarttime());
                    }
                    else
                    {
                        if(currentEmail.equals(slotSlice.getStudent())&& courseCode.equals(slotSlice.getCourse()))
                        {
                            courseListSlices.add(slotSlice.getCourse());
                            keyListSlotSlices.add(dataSnapshot.getKey());
                            slotStudentList.add(slotSlice.getStudent());
                            slotEndTimeList.add(slotSlice.getEndtime());
                            slotStartTimeList.add(slotSlice.getStarttime());

                        }

                    }
                }

                timeTableRecylcerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        timeTableRecylerView.setAdapter(timeTableRecylcerViewAdapter);
        //spinner.setAdapter(adapter);
    }
    private void showDataCoursesSpinner()
    {
        String currentEmail = currentUser.getEmail();

        keysList.clear();
        courseList.clear();
        courseDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Course course = dataSnapshot.getValue(Course.class);
                    if(currentEmail.equals(course.getLecturer())) {
                        courseList.add(course.getCourseCode());
                        keysList.add(dataSnapshot.getKey());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        spinner.setAdapter(adapter);
    }
}