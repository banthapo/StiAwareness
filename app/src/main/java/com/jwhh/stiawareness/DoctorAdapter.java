package com.jwhh.stiawareness;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorViewHolder>{

    private Context context;
    DoctorModel doctorModel;
    DatabaseManager databaseManager;

    private List<String> names ;
    private List<String> pNumber ;
    private List<String> email ;

    public DoctorAdapter(Context context, List<String> names, List<String> pNumber, List<String> email) {
        this.context = context;
       // this.doctorModel = doctorModel;
        this.names = names;
        this.pNumber = pNumber;
        this.email = email;
    }



    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View doctorView = inflater.inflate(R.layout.layout_doctors, parent, false);

        DoctorViewHolder doctorViewHolder = new DoctorViewHolder(doctorView);
        return doctorViewHolder;
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {

        holder.name.setText(names.get(position));
        holder.email.setText(email.get(position));
        holder.pNumber.setText(pNumber.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}