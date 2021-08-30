package com.jwhh.stiawareness;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.stiawareness.databinding.ActivityAvailableDoctorsBinding;

import java.util.ArrayList;

public class AvailableDoctors extends AppCompatActivity implements RecyclerViewAdapter.OnDoctorClickListener, Runnable {
    private ActivityAvailableDoctorsBinding binding;

    private RecyclerView doctorRecyclerView;
    private SearchView doctorSearchView;
    private ImageView backButton;

//    private ImageView searchDoctorName;
//    private EditText searchText;

    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();
    private ArrayList<Integer> pNum = new ArrayList<>();


    private DatabaseManager databaseManager;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        run();

    }

    @Override
    public void onDoctorClick(int position) {
        deleteDoctor(position);
    }

    public int getItemCount() {
        return pNum.size();
    }

    private void deleteDoctor(int position) {
        DatabaseManager databaseManager = new DatabaseManager(this);

        String name = names.get(position);
        int phoneNumber = pNum.get(position) ;

        databaseManager.deleteDoctor(phoneNumber);
        names.remove(position);
        pNum.remove(position);
        email.remove(position);
        adapter.notifyItemRemoved(position);

        Toast.makeText(AvailableDoctors.this, "Deleted:  " + name , Toast.LENGTH_SHORT).show();

        /*doctorList.setOnItemClickListener((parent, view, position, id) -> {

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
        });*/
    }

    private void searchDoctor(String name) {
        databaseManager.doctorSearch(name);
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

        doctorSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadDoctors();
                adapter = new RecyclerViewAdapter(names, email, pNum, this::onDoctorClick);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AvailableDoctors.this));
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    loadDoctors();
                } else {
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

        backButton = findViewById(R.id.from_available_doctors);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AvailableDoctors.this, Awareness.class);
            startActivity(intent);
        });

        loadDoctors();
    }
}
