package com.example.lecturerappointment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterLecturer extends RecyclerView.Adapter<RecyclerViewAdapterLecturer.ReyclerViewHolder>
{

    private ArrayList<ConsultationData> timeTableDataList; //
    private Context context;

    public interface OnItemClickLister
    {
        void onItemClick(int position);

    }

    public RecyclerViewAdapterLecturer(ArrayList<ConsultationData> timeTableDataList, Context context)
    {
        this.timeTableDataList = timeTableDataList;
        this.context = context;

    }
    @NonNull
    @Override
    public RecyclerViewAdapterLecturer.ReyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.time_table_card_lecturer,parent,false);
        return new ReyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterLecturer.ReyclerViewHolder holder, int position)
    {
        int colorBlack =R.color.black;
        int colorWhite=R.color.white;

        ConsultationData timeTableData = timeTableDataList.get(position);

        holder.startTime.setText(timeTableData.getStartTime());

        holder.endTime.setText(timeTableData.getEndTime());

        holder.itemView.setBackgroundColor(context.getResources().getColor(colorBlack));
        holder.startTime.setTextColor(context.getResources().getColor(colorWhite));
        holder.endTime.setTextColor(context.getResources().getColor(colorWhite));

        holder.soltLabel.setText(timeTableData.getTitle());
        holder.soltLabel.setTextColor(context.getResources().getColor(colorWhite));

    }

    @Override
    public int getItemCount()
    {
        return  timeTableDataList.size();
    }

    public class ReyclerViewHolder extends RecyclerView.ViewHolder
    {
        private TextView startTime;
        private TextView endTime;
        private TextView soltLabel;


        public ReyclerViewHolder(@NonNull View itemView)
        {
            super(itemView);
            startTime = itemView.findViewById(R.id.start_time_label);
            endTime = itemView.findViewById(R.id.end_time_label);
            soltLabel= itemView.findViewById(R.id.slot_label);

        }
    }
}
