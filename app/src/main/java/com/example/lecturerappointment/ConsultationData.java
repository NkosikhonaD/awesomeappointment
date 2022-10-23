package com.example.lecturerappointment;

import java.util.HashMap;

public class ConsultationData
{
    private String title;
    private String startTime;
    private String endTime;
    private String userId;
    private String course;
    


    public ConsultationData(String title, String startTime, String endTime,String userId,String course)
    {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.course = course;

    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ConsultationData(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
