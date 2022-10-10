package com.example.lecturerappointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterTimeTable extends RecyclerView.Adapter<RecyclerViewAdapterTimeTable.RecyclerViewHolder>
{
    /**
     * ArrayList timetable grid
     * Context at which this is running
     */
    private ArrayList<ConsultationData> timeTableGridData;
    private Context context;

    /**
     * Constructor
     * @param timeTableGridData timetable data
     * @param context at which this will run
     */
    public RecyclerViewAdapterTimeTable(ArrayList<ConsultationData> timeTableGridData,Context context)
    {
        this.timeTableGridData =timeTableGridData;
        this.context =context;
    }

    /**
     * The individual data view holder to be seen on screen
     * need to have its own layout car is used for this one
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_time_table,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position)
    {
        ConsultationData timeTableData =  timeTableGridData.get(position);
        holder.timeTableLabel.setText(timeTableData.getTitle());


        // if the title of the cell is 0, it means there are no timetable slots
        if(timeTableData.getTitle().equals("0"))
        {
            holder.itemView.setVisibility(View.INVISIBLE);
        }
        changeColor(holder,holder.timeTableLabel.getText().toString());

    }
    public void changeColor(RecyclerViewHolder holder, String title)
    {
        int colorBlack =R.color.black;
        int colorWhite=R.color.white;
        if(title.equalsIgnoreCase("Mon")||title.equalsIgnoreCase("Tue") ||title.equalsIgnoreCase("Wed")||title.equalsIgnoreCase("Thu")|| title.equalsIgnoreCase("Fri")||title.equalsIgnoreCase("Sat"))
        {

            holder.itemView.setBackgroundColor(context.getResources().getColor(colorBlack));
            holder.timeTableLabel.setTextColor(context.getResources().getColor(colorWhite));
        }
    }

    @Override
    public int getItemCount()
    {
        return timeTableGridData.size();
    }

    public class  RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView timeTableLabel;


        public RecyclerViewHolder(@NonNull View itemView)
        {
            super(itemView);

            timeTableLabel = itemView.findViewById(R.id.time_table_label);

            // add listener to grid item
            itemView.setOnClickListener(this);
        }

        /**
         * TimeTable position
         * First row position 0 - 6 is the time 08-10 ,10-12, 12-14hrs
         * First column week days Monday - Saturday the column entries are in position
         * Monday 7 Tuesday 14, Wednesday 21, Thursday 28, Friday 35 and Sartuday 42
         *
         */
        @Override
        public void onClick(View view)
        {
            int position = this.getAdapterPosition();

            switch(position)
            {
                case 8: // monday 8-10
                    Toast.makeText(view.getContext(),"monday 8-10",Toast.LENGTH_SHORT).show();
                    break;
                case 9: // monday 10-12
                    Toast.makeText(view.getContext(),"monday 10-12",Toast.LENGTH_SHORT).show();
                    break;
                case 10: // monday 12-14
                    Toast.makeText(view.getContext(),"monday 12-14",Toast.LENGTH_SHORT).show();
                    break;
                case 11:
                    Toast.makeText(view.getContext(),"monday 14-16",Toast.LENGTH_SHORT).show();
                    break;
                case 12:
                    Toast.makeText(view.getContext(),"monday 16-18",Toast.LENGTH_SHORT).show();
                    break;
                case 13:
                    Toast.makeText(view.getContext(),"monday 18-20",Toast.LENGTH_SHORT).show();
                    break;
                case 15: // Tuesday 8-10
                    Toast.makeText(view.getContext(),"Tuesday 8-10",Toast.LENGTH_SHORT).show();
                    break;
                case 16: // monday 10-12
                    Toast.makeText(view.getContext(),"Tuesday 10-12",Toast.LENGTH_SHORT).show();
                    break;
                case 17: // monday 12-14
                    Toast.makeText(view.getContext(),"Tuesday 12-14",Toast.LENGTH_SHORT).show();
                    break;
                case 18:
                    Toast.makeText(view.getContext(),"Tuesday 14-16",Toast.LENGTH_SHORT).show();
                    break;
                case 19:
                    Toast.makeText(view.getContext(),"Tuesday 16-18",Toast.LENGTH_SHORT).show();
                    break;
                case 20:
                    Toast.makeText(view.getContext(),"Tuesday 14-16",Toast.LENGTH_SHORT).show();
                    break;
                case 22: // Wednesday 8-10
                    Toast.makeText(view.getContext(),"Wednesday 8-10",Toast.LENGTH_SHORT).show();
                    break;
                case 23: // monday 10-12
                    Toast.makeText(view.getContext(),"Wednesday 10-12",Toast.LENGTH_SHORT).show();
                    break;
                case 24: // monday 12-14
                    Toast.makeText(view.getContext(),"Wednesday 12-14",Toast.LENGTH_SHORT).show();
                    break;
                case 25:
                    Toast.makeText(view.getContext(),"Wednesday 14-16",Toast.LENGTH_SHORT).show();
                    break;
                case 26:
                    Toast.makeText(view.getContext(),"Wednesday 16-18",Toast.LENGTH_SHORT).show();
                    break;
                case 27:
                    Toast.makeText(view.getContext(),"Wednesday 14-16",Toast.LENGTH_SHORT).show();
                    break;
                case 29: // Thursday 8-10
                    Toast.makeText(view.getContext(),"Thursday 8-10",Toast.LENGTH_SHORT).show();
                    break;
                case 30: // monday 10-12
                    Toast.makeText(view.getContext(),"Thursday 10-12",Toast.LENGTH_SHORT).show();
                    break;
                case 31: // monday 12-14
                    Toast.makeText(view.getContext(),"Thursday 12-14",Toast.LENGTH_SHORT).show();
                    break;
                case 32:
                    Toast.makeText(view.getContext(),"Thursday 14-16",Toast.LENGTH_SHORT).show();
                    break;
                case 33:
                    Toast.makeText(view.getContext(),"Thursday 16-18",Toast.LENGTH_SHORT).show();
                    break;
                case 34:
                    Toast.makeText(view.getContext(),"Thursday 14-16",Toast.LENGTH_SHORT).show();
                    break;
                case 36: // Friday 8-10
                    Toast.makeText(view.getContext(),"Friday 8-10",Toast.LENGTH_SHORT).show();
                    break;
                case 37: // monday 10-12
                    Toast.makeText(view.getContext(),"Friday 10-12",Toast.LENGTH_SHORT).show();
                    break;
                case 38: // monday 12-14
                    Toast.makeText(view.getContext(),"Friday 12-14",Toast.LENGTH_SHORT).show();
                    break;
                case 39:
                    Toast.makeText(view.getContext(),"Friday 14-16",Toast.LENGTH_SHORT).show();
                    break;
                case 40:
                    Toast.makeText(view.getContext(),"Friday 16-18",Toast.LENGTH_SHORT).show();
                    break;
                case 41:
                    Toast.makeText(view.getContext(),"Friday 14-16",Toast.LENGTH_SHORT).show();
                    break;

                case 43: //Saturday 8-10
                    Toast.makeText(view.getContext(),"Saturday 8-10",Toast.LENGTH_SHORT).show();
                    break;
                case 44: //
                    Toast.makeText(view.getContext(),"Saturday 10-12",Toast.LENGTH_SHORT).show();
                    break;
                case 45: //  12-14
                    Toast.makeText(view.getContext(),"Saturday 12-14",Toast.LENGTH_SHORT).show();
                    break;
                case 46:
                    Toast.makeText(view.getContext(),"Saturday 14-16",Toast.LENGTH_SHORT).show();
                    break;
                case 47:
                    Toast.makeText(view.getContext(),"Saturday 16-18",Toast.LENGTH_SHORT).show();
                    break;
                case 48:
                    Toast.makeText(view.getContext(),"Saturday 14-16",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    }
}
