package com.example.lecturerappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddCoursesActivity extends AppCompatActivity
{

    private ImageButton addCourseButton;
    private Button saveButton;
    private EditText courseNameEdit;
    private EditText courseCodeEdit;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth userAuth;
    private DatabaseReference databaseReference = database.getReference();
    private FirebaseUser user;

    int colorRed = com.google.android.material.R.color.design_default_color_error;
    int colorPrimary = com.google.android.material.R.color.design_default_color_primary;
    int white = R.color.white;
    private String userEmail ="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        addCourseButton = findViewById(R.id.add_image_button);
        saveButton =findViewById(R.id.button_save_course);
        courseNameEdit= findViewById(R.id.edit_text_course_name);
        courseCodeEdit= findViewById(R.id.edit_text_course_code);

        courseNameEdit.setVisibility(View.INVISIBLE);
        courseCodeEdit.setVisibility(View.INVISIBLE);

        userAuth = FirebaseAuth.getInstance();

        user = userAuth.getCurrentUser();
        userEmail = user.getEmail();

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                courseNameEdit.setVisibility(View.VISIBLE);
                courseCodeEdit.setVisibility(View.VISIBLE);
                saveButton.setText("Save");
                saveButton.setBackgroundColor(AddCoursesActivity.this.getResources().getColor(colorPrimary));
                saveButton.setTextColor(AddCoursesActivity.this.getResources().getColor(white));
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(saveButton.getText().toString().equals("Save"))
                {
                    String courseName = courseNameEdit.getText().toString();
                    String courseCode = courseNameEdit.getText().toString();
                    if(courseName.trim().isEmpty())
                    {
                        courseNameEdit.setError("Course name cannot be empty");
                        return;
                    }
                    if(courseCode.trim().isEmpty())
                    {
                        courseNameEdit.setError(" Course Code code cannot be empty");
                        return;
                    }
                    else
                    {

                        HashMap<String, String> coursesMap = new HashMap<>();

                        coursesMap.put("lecturer",userEmail);
                        coursesMap.put("courseName",courseName);
                        coursesMap.put("courseCode",courseCode);
                        databaseReference.child("courses").push().setValue(coursesMap);

                        Toast.makeText(AddCoursesActivity.this,""+courseName+" "+courseCode+"saved to database",Toast.LENGTH_SHORT).show();

                        saveButton.setText("Exit");
                        courseNameEdit.getText().clear();
                        courseCodeEdit.getText().clear();
                        courseNameEdit.setVisibility(View.INVISIBLE);
                        courseCodeEdit.setVisibility(View.INVISIBLE);
                        saveButton.setBackgroundColor(AddCoursesActivity.this.getResources().getColor(colorRed));
                        saveButton.setTextColor(AddCoursesActivity.this.getResources().getColor(white));
                        Toast.makeText(AddCoursesActivity.this,"click add to create new course or exit",Toast.LENGTH_SHORT).show();

                    }

                }
                else
                {
                    saveButton.setText("Save");
                    saveButton.setBackgroundColor(AddCoursesActivity.this.getResources().getColor(colorPrimary));
                    saveButton.setTextColor(AddCoursesActivity.this.getResources().getColor(white));
                    courseNameEdit.setVisibility(View.INVISIBLE);
                    courseCodeEdit.setVisibility(View.INVISIBLE);

                }

            }
        });


    }
}