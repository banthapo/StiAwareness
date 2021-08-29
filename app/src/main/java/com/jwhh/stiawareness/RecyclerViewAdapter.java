package com.jwhh.stiawareness;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements View.OnClickListener{

    private Context context;
    private Cursor cursor;
    private ArrayList<String> names;
    private ArrayList<String> email;
    private ArrayList<String> pNum;
    private OnDoctorClickListener onDoctorClickListener;
    private AvailableDoctors availableDoctors;


    public RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> email, ArrayList<String> pNum, OnDoctorClickListener onDoctorClickListener) {
        this.context = context;
        this.names = names;
        this.email = email;
        this.pNum = pNum;
        this.onDoctorClickListener = onDoctorClickListener;
    }

    @Override
    public void onClick(View v) {
//        availableDoctors = new AvailableDoctors();
        String phoneNumber = (String) v.getTag();
    }

    public interface OnDoctorClickListener {
        void onDoctorClick(int position);
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View doctorView = inflater.inflate(R.layout.layout_doctors, parent, false);

        RecyclerViewHolder doctorHolder = new RecyclerViewHolder(doctorView, onDoctorClickListener);
        return doctorHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder doctorHolder, int position) {

            doctorHolder.name.setText(names.get(position));
            doctorHolder.email.setText(email.get(position));
            doctorHolder.pNumber.setText(pNum.get(position));

//            int newPosition = Integer.parseInt(DatabaseManager.DOCTOR_PHONE_NUMBER) + position;

            /*if(){
                int secPos = cursor.getColumnIndex(DatabaseManager.DOCTOR_PHONE_NUMBER) + position;

                if (cursor.moveToPosition(secPos)) {
                    int phoneNumber = cursor.getInt(cursor.getColumnIndex(DatabaseManager.DOCTOR_PHONE_NUMBER));
                    doctorHolder.itemView.setTag(phoneNumber);
                }
            }*/
    }


    @Override
    public int getItemCount() {
        return pNum.size();
    }
}