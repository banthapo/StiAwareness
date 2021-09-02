package com.jwhh.stiawareness.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.stiawareness.R;
import com.jwhh.stiawareness.database.DatabaseManager;
import com.jwhh.stiawareness.databinding.ActivityAvailableDoctorsBinding;
import com.jwhh.stiawareness.recyclerview.RecyclerViewAdapter;

import java.util.ArrayList;

public class AvailableDoctors extends AppCompatActivity implements RecyclerViewAdapter.OnDoctorClickListener, Runnable {
    private ActivityAvailableDoctorsBinding binding;

    private SearchView doctorSearchView;
    private ImageView backButton;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();
    private ArrayList<Integer> pNum = new ArrayList<>();

    private DatabaseManager databaseManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private Button message, call;
    private TextView docName;

    PopupWindow popupWindow = null;
    private int clicked_doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        run();

    }

    public void showPopup(int position){

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.clicked_doctor, null);
        TextView docName = (TextView) layout.findViewById(R.id.clicked_dr_view);

        popupWindow = new PopupWindow(this);
        popupWindow.setContentView(layout);getDataDir();
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(null);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 2, 0);

        String name = names.get(position);
        docName.setText(name);

        Toast.makeText(AvailableDoctors.this, "Deleted:  " +  name, Toast.LENGTH_SHORT).show();
//        doctorName.setText(names.get(position));
    }

    @Override
    public void onDoctorClick(int position){
        showPopup(position);
//        deleteDoctor(position);
    }

    private void deleteDoctor(int position) {

        String name = names.get(position);
        int phoneNumber = pNum.get(position) ;

        databaseManager.deleteDoctor(phoneNumber);
        names.remove(position);
        pNum.remove(position);
        email.remove(position);
        adapter.notifyItemRemoved(position);

        Toast.makeText(AvailableDoctors.this, "Deleted:  " + name , Toast.LENGTH_SHORT).show();

    }

    public void searchDoctor() {
        doctorSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    loadDoctors();

                } else {
                    loadDoctors();
                    adapter = new RecyclerViewAdapter(names, email, pNum, this::onDoctorClick);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(AvailableDoctors.this));
                    adapter.getFilter().filter(newText);

                }
                return true;
            }

            private void onDoctorClick(int pos) {
            }
        });
    }

    public void loadDoctors(){
        databaseManager = new DatabaseManager(AvailableDoctors.this);

        names = databaseManager.getDoctorName();
        email = databaseManager.getDoctorEmail();
        pNum = databaseManager.getDoctorPhoneNumber();

        recyclerview();
    }

    private  void recyclerview(){
        adapter = new RecyclerViewAdapter(names, email, pNum, this::onDoctorClick);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AvailableDoctors.this));

    }

    public void run() {

        binding = ActivityAvailableDoctorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.doctor_recyclerview);
        doctorSearchView = findViewById(R.id.doctor_search_view);

        message = findViewById(R.id.message);
        call = findViewById(R.id.call);

        backButton = findViewById(R.id.from_available_doctors);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AvailableDoctors.this, Awareness.class);
            startActivity(intent);
        });
        searchDoctor();
        loadDoctors();
    }

    public int getItemCount() {
        return pNum.size();
    }

}

