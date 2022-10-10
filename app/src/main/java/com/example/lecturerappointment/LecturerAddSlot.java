package com.example.lecturerappointment;

import static androidx.recyclerview.widget.LinearLayoutManager.*;

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

import java.util.ArrayList;
import java.util.Calendar;

public class LecturerAddSlot extends AppCompatActivity
{

    private RecyclerViewAdapterLecturer slotsRecyclerViewAdapter;
    private RecyclerView slotsRecylcerView;
    private Spinner spinnerDay;
    private Spinner spinnerCourse;
    private RecyclerViewAdapterLecturer.OnItemClickLister onItemClickLister;
    private ArrayList<ConsultationData> consultationDataArrayList;
    private ImageView save;
    private ImageView add;

    private Button setStartTimeButton;
    private Button setEndTimeButton;

    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private String timeSelected;
    String daySelected;
    String courseSelected;
    private int currentHour, currentMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_add_slot);
        spinnerDay = findViewById(R.id.spinnerday);
        spinnerCourse =findViewById(R.id.spinnercourse);
        timeSelected ="Default time";
        save = findViewById(R.id.save);
        add = findViewById(R.id.add);
        daySelected= "Default day";
        courseSelected= "Default course";

        setStartTimeButton = findViewById(R.id.button_start_time);
        setEndTimeButton = findViewById(R.id.button_end_time);

        editTextStartTime = findViewById(R.id.start_time_edit);
        editTextEndTime= findViewById(R.id.edit_end_time);
        // These courses should come from database
        ArrayAdapter<CharSequence> spinnerAdapterCourses = ArrayAdapter.createFromResource(LecturerAddSlot.this,R.array.courses_registered, android.R.layout.simple_spinner_item);

        // Week day should come from selection made by user from the grid time table.
        ArrayAdapter<CharSequence> spinnerAdapterWeekDays = ArrayAdapter.createFromResource(LecturerAddSlot.this,R.array.week_days,android.R.layout.simple_spinner_item) ;

        spinnerAdapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapterWeekDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        

        spinnerDay.setAdapter(spinnerAdapterWeekDays);
        spinnerCourse.setAdapter(spinnerAdapterCourses);

        spinnerCourse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l)
            {
                courseSelected = spinnerAdapterCourses.getItem(position).toString();
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
                TimePickerDialog timePickerDialog = new TimePickerDialog(LecturerAddSlot.this, new TimePickerDialog.OnTimeSetListener()
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
                //final Calendar c = Calendar.getInstance();
                String  time = editTextStartTime.getText().toString();
                int h = Integer.parseInt(time.split(":")[0]);
                int m = Integer.parseInt(time.split(":")[1]);

                TimePickerDialog timePickerDialog = new TimePickerDialog(LecturerAddSlot.this, new TimePickerDialog.OnTimeSetListener()
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
                // clear selections
                spinnerDay.setSelection(0);
                spinnerCourse.setSelection(0);
                editTextEndTime.setText("00:00");
                editTextStartTime.setText("00:00");

                Toast.makeText(LecturerAddSlot.this, "Create a new slot, select Course, Day and Times", Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // write  all slots to database and exit
                String day = daySelected;
                String course = courseSelected;
                String startTime = editTextStartTime.getText().toString();
                String endTime = editTextEndTime.getText().toString();

                consultationDataArrayList.add(new ConsultationData(day+" "+course,startTime,endTime));
                slotsRecyclerViewAdapter.notifyItemInserted(consultationDataArrayList.size());
                Toast.makeText(LecturerAddSlot.this, "slot saved", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void addNewSlot()
    {
       // create a time picker here set the text of the start / end time to the time picked
    }
}