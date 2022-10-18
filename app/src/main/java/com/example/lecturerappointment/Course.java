package com.example.lecturerappointment;

public class Course
{
    String courseCode;
    String courseName;
    String lecturer;

    public Course()
    {
    }

    public Course(String courseCode, String courseName, String lecturer)
    {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.lecturer = lecturer;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer)
    {
        this.lecturer = lecturer;
    }
}
