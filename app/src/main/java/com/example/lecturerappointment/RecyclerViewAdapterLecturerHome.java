package com.example.lecturerappointment;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewAdapterLecturerHome extends RecyclerView.Adapter<RecyclerViewAdapterLecturerHome.RecyclerViewHolder>
{
    private ArrayList<GridData> gridDataArrayLis;
    private Context context;

    public RecyclerViewAdapterLecturerHome(ArrayList<GridData> gridDataArrayLis, Context context) {
        this.gridDataArrayLis = gridDataArrayLis;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position)
    {
        GridData gridData = gridDataArrayLis.get(position);
        holder.gridLabel.setText(gridData.getTitle());
        holder.gridImage.setImageResource(gridData.getGridImage());
    }

    @Override
    public int getItemCount() {
        return gridDataArrayLis.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView gridLabel;
        private ImageView gridImage;


        public RecyclerViewHolder(@NonNull View itemView){
            super(itemView);
            gridImage = itemView.findViewById(R.id.gridimage);
            gridLabel = itemView.findViewById(R.id.gridlabel);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            int position = this.getAdapterPosition();
           switch(position)
           {
               case 0:
                   Toast.makeText(view.getContext(),"Add courses activity",Toast.LENGTH_SHORT).show();
                   Intent addCourseIntent = new Intent(view.getContext(),AddCoursesActivity.class);

                   break;
               case 1:
                   // here view slots and have options to add new slots
                   Toast.makeText(view.getContext()," Slots view and add new slots activity",Toast.LENGTH_SHORT).show();
                   Intent addSlot = new Intent(view.getContext(),LecturerAddSlot.class);
                   break;
               case 2:
                   // chats
                   Toast.makeText(view.getContext(),"chats",Toast.LENGTH_SHORT).show();
                   break;
               case 4:
                   Toast.makeText(view.getContext(), "Notifications",Toast.LENGTH_SHORT).show();
                   break;

           }




        }
    }
}
