package com.example.lecturerappointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddCoursesActivity extends AppCompatActivity
{

    private ImageButton addCourseButton;
    private Button saveButton;
    private EditText courseNameEdit;
    private EditText courseCodeEdit;

    int colorRed = com.google.android.material.R.color.design_default_color_error;
    int colorPrimary = com.google.android.material.R.color.design_default_color_primary;


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
        courseNameEdit.setVisibility(View.INVISIBLE);

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                courseNameEdit.setVisibility(View.VISIBLE);
                courseNameEdit.setVisibility(View.VISIBLE);
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
                        Toast.makeText(AddCoursesActivity.this,""+courseName+""+courseCode+"saved to database",Toast.LENGTH_SHORT).show();
                        saveButton.setText("Exit");

                        saveButton.setBackgroundColor(AddCoursesActivity.this.getResources().getColor(colorRed));
                    }

                }
                else
                {
                    saveButton.setText("Save");
                    saveButton.setBackgroundColor(AddCoursesActivity.this.getResources().getColor(colorPrimary));

                    courseNameEdit.setVisibility(View.INVISIBLE);
                    courseNameEdit.setVisibility(View.INVISIBLE);

                }

            }
        });


    }
}