package com.example.lecturerappointment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{
    private ArrayList<GridData> gridDataList;
    private Context context;
    public RecyclerViewAdapter(ArrayList<GridData> gridDataList, Context context)
    {
        this.context = context;
        this.gridDataList = gridDataList;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position)
    {
        GridData gridData = gridDataList.get(position);
        holder.gridLabel.setText(gridData.getTitle());
        holder.gridImage.setImageResource(gridData.getGridImage());

    }

    @Override
    public int getItemCount()
    {
        return gridDataList.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView gridLabel;
        private ImageView gridImage;

        public RecyclerViewHolder(@NonNull View itemView)
        {
            super(itemView);
            gridImage = itemView.findViewById(R.id.gridimage);
            gridLabel= itemView.findViewById(R.id.gridlabel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            int position =  this.getAdapterPosition();
            // position 0 = Consult
            // position 1 = Contact lecturer
            // position 2 = Class timetable
            // position 3 = Notice board
            switch (position)
            {
                case 0:
                    Intent consultIntent = new Intent(view.getContext(),Consult.class);
                    context.startActivity(consultIntent);
                    break;

                case 1:
                    Intent addSlotIntent = new Intent(view.getContext(),LecturerAddSlot.class);
                    context.startActivity(addSlotIntent);
                    break;
                case 2:
                    Toast.makeText(view.getContext(), "Class timetable clicked",Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(view.getContext(), "Notice board clicked",Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    }

}
