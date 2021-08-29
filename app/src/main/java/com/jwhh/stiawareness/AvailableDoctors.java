package com.jwhh.stiawareness;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.stiawareness.databinding.ActivityAvailableDoctorsBinding;

import java.util.ArrayList;
import java.util.List;

public class AvailableDoctors extends AppCompatActivity implements RecyclerViewAdapter.OnDoctorClickListener {
    private ActivityAvailableDoctorsBinding binding;

    private ListView doctorList;
    private ImageView backButton;

    private ImageView searchDoctorName;
    private EditText searchText;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();
    private ArrayList<String> pNum = new ArrayList<>();


    private DatabaseManager databaseManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private Context context;
    private Cursor cursor;

    public AvailableDoctors(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public AvailableDoctors() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        run();

        binding = ActivityAvailableDoctorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        adapter = new RecyclerViewAdapter(AvailableDoctors.this, names, email, pNum, this::onDoctorClick);
        recyclerView = findViewById(R.id.recycler_view);
        searchDoctorName = findViewById(R.id.search_doctor);
        searchText = findViewById(R.id.search_doctor_text);

        doctorList = findViewById(R.id.doctor_list_view);
        backButton = findViewById(R.id.from_available_doctors);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AvailableDoctors.this, Awareness.class);
            startActivity(intent);
        });

        loadDoctors();

//        deleteDoctor();

        searchDoctor();
    }

    @Override
    public void onDoctorClick(int position) {
//        int newPosition = databaseManager.firstPosition();
        names.remove(position);
        pNum.remove(position);
        email.remove(position);
        adapter.notifyItemRemoved(position);

        Toast.makeText(AvailableDoctors.this, "Delete" + position , Toast.LENGTH_LONG).show();

    }
    public static String delete(int phoneNumber){

        String number = String.valueOf(phoneNumber);
        return number;
    }

    public int getItemCount() {
        return pNum.size();
    }

    private void loadDoctors(){
        DatabaseManager databaseManager = new DatabaseManager(AvailableDoctors.this);

        names = databaseManager.getDoctorName();
        email = databaseManager.getDoctorEmail();
        pNum = databaseManager.getDoctorPhoneNumber();

        recyclerview();
    }

    private  void recyclerview(){
        adapter = new RecyclerViewAdapter(AvailableDoctors.this, names, email, pNum, this::onDoctorClick);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AvailableDoctors.this));

    }


    private void deleteDoctor() {
        doctorList.setOnItemClickListener((parent, view, position, id) -> {

            try {
                String clickedDoctor = (String) parent.getItemAtPosition(position);
                int number = databaseManager.getPhoneNumber(clickedDoctor);

//                databaseManager.deleteDoctor(String.valueOf(number));

                List<DoctorModel> deletedDoctor = databaseManager.getDoctorDetails(String.valueOf(number));

                Toast.makeText(AvailableDoctors.this, "Successfully deleted" + deletedDoctor.toString(), Toast.LENGTH_LONG).show();

                loadDoctors();
            } catch (Exception e) {
                Toast.makeText(AvailableDoctors.this, "Delete failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void searchDoctor() {
        searchDoctorName.setOnClickListener(new View.OnClickListener() {
            private DatabaseManager databaseManager;

            @Override
            public void onClick(View v) {
                try {
                    databaseManager = new DatabaseManager(AvailableDoctors.this);
                    String search = searchText.getText().toString();

                    List<String> searchedDoctor = this.databaseManager.doctorSearch(search);
                    ArrayAdapter doctorArrayAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1, searchedDoctor);


                    doctorList.setAdapter(doctorArrayAdapter);

                } catch (Exception e){
                   loadDoctors();
                }
            }
        });
    }


  /*  public void displayDoctorName(){
        try {
            databaseManager = new DatabaseManager(AvailableDoctors.this);

            List<String> allDoctors = databaseManager.getDoctorName();
            ArrayAdapter doctorArrayAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1 , allDoctors);

            doctorList.setAdapter( doctorArrayAdapter);
        } catch (Exception e){}

    }*/



    public void grun() {

        binding = ActivityAvailableDoctorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.recycler_view);
        searchDoctorName = findViewById(R.id.search_doctor);
        searchText = findViewById(R.id.search_doctor_text);

        doctorList = findViewById(R.id.doctor_list_view);
        backButton = findViewById(R.id.from_available_doctors);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AvailableDoctors.this, Awareness.class);
            startActivity(intent);
        });

        loadDoctors();

//        deleteDoctor();

        searchDoctor();
    }


}
