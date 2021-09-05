package com.jwhh.stiawareness.recyclerview;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.stiawareness.app.AvailableDoctors;
import com.jwhh.stiawareness.R;

import java.util.ArrayList;
import java.util.Collection;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener , Filterable {

    //declaring field variables
    private ArrayList<String> names;
    private ArrayList<String> email;
    private ArrayList<Integer> pNum;
    private OnDoctorClickListener onDoctorClickListener;

    private ArrayList<String> allDoctors;
    AvailableDoctors doctors = new AvailableDoctors();

    //setting up the constructor
    public RecyclerViewAdapter(ArrayList<String> names, ArrayList<String> email, ArrayList<Integer> pNum, OnDoctorClickListener onDoctorClickListener) {
        this.names = names;
        this.email = email;
        this.pNum = pNum;
        this.allDoctors = new ArrayList<>(names);
        this.onDoctorClickListener = onDoctorClickListener;
    }

    //onClick view listener
    @Override
    public void onClick(View v) {

    }

    //getting filter for recyclerview search
    @Override
    public Filter getFilter() {
        return filter;
    }

    //setting up the filter for searching doctor
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<String> filteredDoctors = new ArrayList<>();

            if (constraint.toString().isEmpty()){
               doctors.loadDoctors();

            } else {
                for (int i = 0, allDoctorsSize = allDoctors.size(); i < allDoctorsSize; i++) {
                    String doctor = allDoctors.get(i);

                    if(doctor.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredDoctors.add(doctor);
                    }

                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredDoctors;

            return filterResults;
        }

        //publishing filtered doctors
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            names.clear();
            if (constraint.toString().isEmpty()){
                return;
            }else {
                names.addAll((Collection<? extends String>) results.values);
                notifyDataSetChanged();
            }
        }
    };

    //creating an interface for onClick listener in recyclerview
    public interface OnDoctorClickListener {
        void onDoctorClick(int position);
    }

    //inflating layout on creating viewHolder
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View doctorView = inflater.inflate(R.layout.layout_doctors, parent, false);

        RecyclerViewHolder doctorHolder = new RecyclerViewHolder(doctorView, onDoctorClickListener);
        return doctorHolder;
    }

    //setting values for viewHolder
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerViewHolder doctorHolder, int position) {

            doctorHolder.name.setText(names.get(position));
            doctorHolder.email.setText(email.get(position));
            doctorHolder.pNumber.setText(pNum.get(position).toString());

    }

    //getting the size of name list
    @Override
    public int getItemCount() {
        return names.size();
    }
}