package com.jwhh.stiawareness;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView name, email, pNumber;
    public CardView doctorLayout;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.name);
        email = itemView.findViewById(R.id.email);
        pNumber = itemView.findViewById(R.id.number);

//        doctorLayout = itemView.findViewById(R.id.doctor_recyclerview);
    }
}

