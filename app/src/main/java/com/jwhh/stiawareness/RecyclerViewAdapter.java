package com.jwhh.stiawareness;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private Context context;
    private List<DoctorModel> doctorModel;

    public RecyclerViewAdapter(Context context, List<DoctorModel> doctorModel) {
        this.context = context;
        this.doctorModel = doctorModel;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View doctorView = inflater.inflate(R.layout.layout_doctors, parent, false);

        return new RecyclerViewHolder(doctorView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.name.setText(doctorModel.get(position).getName());
        holder.email.setText(doctorModel.get(position).getEmailAddress());
        holder.pNumber.setText(doctorModel.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return doctorModel.size();
    }
}