package com.example.lecturerappointment;

public class ConsultationData
{
    private String title;
    private String startTime;
    private String endTime;

    public ConsultationData(String title, String startTime, String endTime)
    {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
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
