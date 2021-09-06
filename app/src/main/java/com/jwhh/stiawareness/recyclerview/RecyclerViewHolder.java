package com.jwhh.stiawareness.recyclerview;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.stiawareness.R;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //declaring field variables
    public TextView name, email, pNumber;
    public RelativeLayout doctorLayout;


    public RecyclerViewAdapter.OnDoctorClickListener onDoctorClickListener;

    //setting the constructor for viewHolder
    public RecyclerViewHolder(View itemView, RecyclerViewAdapter.OnDoctorClickListener onDoctorClickListener) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        email = itemView.findViewById(R.id.email);
        pNumber = itemView.findViewById(R.id.number);
        doctorLayout = itemView.findViewById(R.id.doctor_view_layout);

        this.onDoctorClickListener = onDoctorClickListener;
        itemView.setOnClickListener(this);
    }

    //setting a click listener to view onClick
    @Override
    public void onClick(View v) {
        onDoctorClickListener.onDoctorClick(getAdapterPosition());
    }
}

