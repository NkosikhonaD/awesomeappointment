package com.example.lecturerappointment.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lecturerappointment.GridData;
import com.example.lecturerappointment.R;
import com.example.lecturerappointment.RecyclerViewAdapter;
import com.example.lecturerappointment.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<GridData> gridDataList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        //HomeViewModel homeViewModel =
         //       new ViewModelProvider(this).get(HomeViewModel.class);
        gridDataList = new ArrayList<>();

        gridDataList.add(new GridData("Consult", R.drawable.ic_baseline_supervised_user_circle_24));
        gridDataList.add(new GridData("Lecture Contact",R.drawable.ic_baseline_contact_phone_24));
        gridDataList.add(new GridData("Time Table",R.drawable.ic_baseline_calendar_month_24));
        gridDataList.add(new GridData("Notice board",R.drawable.ic_baseline_info_24));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(gridDataList,getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        recyclerView = binding.recyclerid;
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);


        View root = binding.getRoot();
        // final TextView textView = binding.textHome;
        // homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}