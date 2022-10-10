package com.example.lecturerappointment;

public class GridData
{
    private String title;
    private int gridImage;

    public GridData()
    {
        title= "Default title";
        gridImage = 0;

    }
    public GridData(String title, int gridImage)
    {
        this.title= title;
        this.gridImage =gridImage ;

    }
    public void setGridImage(int gridImage)
    {
        this.gridImage = gridImage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public int getGridImage() {
        return gridImage;
    }
}
