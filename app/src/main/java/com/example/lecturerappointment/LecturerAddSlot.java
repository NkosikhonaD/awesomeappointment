package com.example.lecturerappointment;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.TimePickerDialog;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class LecturerAddSlot extends AppCompatActivity
{
    private FirebaseDatabase database;
    private FirebaseAuth loggedUser;
    private DatabaseReference databaseReferenceCourses;
    private DatabaseReference databaseReferenceSlotScices;
    private FirebaseUser currentUser;

    private List<CharSequence> keysList;

    private RecyclerViewAdapterLecturer slotsRecyclerViewAdapter;
    private RecyclerView slotsRecylcerView;
    private Spinner spinnerDay;
    private Spinner spinnerCourse;
    private RecyclerViewAdapterLecturer.OnItemClickLister onItemClickLister;
    private ArrayList<ConsultationData> consultationDataArrayList;

    private ImageView add;

    private Button setStartTimeButton;
    private Button setEndTimeButton;
    ArrayAdapter<CharSequence> adapter;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private String timeSelected;
    String daySelected;
    String courseSelected;
    private int currentHour, currentMinute;
    HashMap<String,String> slotsHashMap;
    HashMap<String,String> slotSlicesMap;
    private List<CharSequence> coursesSpinnerList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_add_slot);


        coursesSpinnerList= new ArrayList<>();
        keysList = new ArrayList<>();

        spinnerDay = findViewById(R.id.spinnerday);
        spinnerCourse =findViewById(R.id.spinnercourse);
        timeSelected ="Default time";

        add = findViewById(R.id.add);
        daySelected= "Default day";
        courseSelected= "Default course";

        setStartTimeButton = findViewById(R.id.button_start_time);
        setEndTimeButton = findViewById(R.id.button_end_time);

        editTextStartTime = findViewById(R.id.start_time_edit);
        editTextEndTime= findViewById(R.id.edit_end_time);

        loggedUser= FirebaseAuth.getInstance();

        currentUser = loggedUser.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        databaseReferenceCourses = database.getReference("courses");
        databaseReferenceSlotScices =database.getReference("Slot_slices");
        adapter = new ArrayAdapter<CharSequence>(LecturerAddSlot.this, android.R.layout.simple_spinner_item,coursesSpinnerList);

        ArrayAdapter<CharSequence> spinnerAdapterWeekDays = ArrayAdapter.createFromResource(LecturerAddSlot.this,R.array.week_days,android.R.layout.simple_spinner_item) ;


        spinnerAdapterWeekDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinnerDay.setAdapter(spinnerAdapterWeekDays);
        spinnerCourse.setAdapter(adapter);
        showData();

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                courseSelected = adapter.getItem(position).toString();
                spinnerCourse.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                daySelected =   spinnerAdapterWeekDays.getItem(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });

        slotsRecylcerView = findViewById(R.id.recyclerViewLTimeTable);
        consultationDataArrayList = new ArrayList<>();
        slotsRecyclerViewAdapter = new RecyclerViewAdapterLecturer(consultationDataArrayList,LecturerAddSlot.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LecturerAddSlot.this, VERTICAL,false);

        slotsRecylcerView.setLayoutManager(layoutManager);
        slotsRecylcerView.setAdapter(slotsRecyclerViewAdapter);

        setStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final Calendar c = Calendar.getInstance();
                currentHour = c.get(Calendar.HOUR);
                currentMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(LecturerAddSlot.this,1, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute)
                    {
                        String time = String.format("%02d",hour)+":"+String.format("%02d",minute);
                        editTextStartTime.setText(time);
                    }
                },currentHour,currentMinute,true);
                timePickerDialog.setTitle("Set Start  time");
                timePickerDialog.show();
            }
        });

        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String  time = editTextStartTime.getText().toString();
                int h = Integer.parseInt(time.split(":")[0]);
                int m = Integer.parseInt(time.split(":")[1]);

                TimePickerDialog timePickerDialog = new TimePickerDialog(LecturerAddSlot.this,1, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute)
                    {

                        String time = String.format("%02d",hour)+":"+String.format("%02d",minute);
                        editTextEndTime.setText(time);
                    }
                },h,m,true);
                timePickerDialog.setTitle("Set End time");

                timePickerDialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                addNewSlot();
                // clear selections
                spinnerDay.setSelection(0);
                spinnerCourse.setSelection(0);
                editTextEndTime.setText("00:00");
                editTextStartTime.setText("00:00");

                Toast.makeText(LecturerAddSlot.this, "Create a new slot, select Course, Day and Times", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void addNewSlot()
    {
        String day = daySelected;
        String course = courseSelected;
        String startTime = editTextStartTime.getText().toString();
        String endTime = editTextEndTime.getText().toString();
        String lectureEmail= "";
        consultationDataArrayList.add(new ConsultationData(day+" "+course,startTime,endTime));
        slotsRecyclerViewAdapter.notifyItemInserted(consultationDataArrayList.size());
        slotsHashMap= new HashMap<>();

        long munites = getTimeDifference(startTime,endTime);
        if(currentUser!=null&& munites>=10)
        {
            // check for same time same lecturer same course slots..
            lectureEmail = currentUser.getEmail();
            slotsHashMap.put("lecturer",lectureEmail);
            slotsHashMap.put("course",course);
            slotsHashMap.put("day",day);
            slotsHashMap.put("starttime",startTime);
            slotsHashMap.put("endtime",endTime);
            //check if slots does nt exist already.
            databaseReferenceCourses.push().setValue(slotsHashMap);

            createSlotSlices(startTime,endTime,day,course,lectureEmail);
        }



    }

    private void showData()
    {
        String currentEmail = currentUser.getEmail();

        keysList.clear();
        coursesSpinnerList.clear();
        databaseReferenceCourses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    Course course = dataSnapshot.getValue(Course.class);
                    if(currentEmail.equals(course.getLecturer())) {
                        coursesSpinnerList.add(course.getCourseCode());
                        keysList.add(dataSnapshot.getKey());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //adapter.notifyDataSetChanged();
        spinnerCourse.setAdapter(adapter);
    }

    public long getTimeDifference(String startTime, String endTime)
    {
        long minutes = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            minutes= (Duration.between(LocalTime.parse(startTime),LocalTime.parse(endTime)).getSeconds())/60;
        }

        return minutes;
    }
    public void createSlotSlices(String startTime, String endTime,String day,String course,String lecturer)
    {
        slotSlicesMap = new HashMap<>();
        long minutes =getTimeDifference(startTime,endTime);
        LocalTime start;
        LocalTime slotEndTime = null;
        String lectureEmail= currentUser.getEmail();
        LocalTime end;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
           start = LocalTime.parse(startTime);
           end = LocalTime.parse(endTime);
            slotEndTime= start;
        }
        int totalSlots =(int)(minutes/15);
        for(int i=0;i<totalSlots;i++)
        {

            slotSlicesMap.put("SlotName","Slot_slice"+(i+1));
            slotSlicesMap.put("StartTime",slotEndTime.toString());
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
            {
                slotEndTime = slotEndTime.plusMinutes(15);
                slotSlicesMap.put("EndTime",slotEndTime.toString());
            }

            slotSlicesMap.put("lecturer",lecturer);
            slotSlicesMap.put("day",day);
            slotSlicesMap.put("course", course);
            slotSlicesMap.put("student","student");
            databaseReferenceSlotScices.push().setValue(slotSlicesMap);
        }

    }


}