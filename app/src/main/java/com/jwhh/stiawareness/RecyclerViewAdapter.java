package com.jwhh.stiawareness;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private Context context;
    DoctorModel doctorModel;
    DatabaseManager databaseManager;

    private ArrayList<String> names ;
    private ArrayList<String> pNumber ;
    private ArrayList<String> email ;
    private RecyclerView recyclerView;

    public RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> pNumber, ArrayList<String> email) {
        this.context = context;
       // this.doctorModel = doctorModel;
        this.names = names;
        this.pNumber = pNumber;
        this.email = email;
    }



    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View doctorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_doctors, parent, false);
        RecyclerViewHolder doctorViewHolder = new RecyclerViewHolder(doctorView);

        return doctorViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.name.setText(names.get(position));
        holder.email.setText(email.get(position));
        holder.pNumber.setText(pNumber.get(position));

    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}