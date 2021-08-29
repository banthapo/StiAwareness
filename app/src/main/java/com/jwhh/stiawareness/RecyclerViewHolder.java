package com.jwhh.stiawareness;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView name, email, pNumber;
    public RelativeLayout doctorLayout;

    public RecyclerViewAdapter.OnDoctorClickListener onDoctorClickListener;

    public RecyclerViewHolder(View itemView, RecyclerViewAdapter.OnDoctorClickListener onDoctorClickListener) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        email = itemView.findViewById(R.id.email);
        pNumber = itemView.findViewById(R.id.number);

        this.onDoctorClickListener = onDoctorClickListener;
        itemView.setOnClickListener(this);
        doctorLayout = itemView.findViewById(R.id.doctor_view_layout);
    }



    @Override
    public void onClick(View v) {
        onDoctorClickListener.onDoctorClick(getAdapterPosition());
    }
}

