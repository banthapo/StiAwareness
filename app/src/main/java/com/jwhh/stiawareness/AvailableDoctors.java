package com.jwhh.stiawareness;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.stiawareness.databinding.ActivityAvailableDoctorsBinding;

import java.util.ArrayList;

public class AvailableDoctors extends AppCompatActivity {
    private ActivityAvailableDoctorsBinding binding;

//    private ListView doctorList;
    private ArrayList<DoctorModel> listDoctors;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView searchDoctorName;
    private EditText searchText;
    private ArrayList<String> doctorNames = new ArrayList<>();
    private ArrayList<String> doctorEmail = new ArrayList<>();
    private ArrayList<String> doctorNumber = new ArrayList<>();

    private DatabaseManager databaseManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAvailableDoctorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        try{
            searchDoctorName = findViewById(R.id.search_doctor);
            recyclerView = findViewById(R.id.doctor_recyclerview);
            searchText = findViewById(R.id.search_doctor_text);

            gettingDoctors();
            displayDoctorInfo();

            searchDoctorName.setOnClickListener(v -> {
                String text = searchText.getText().toString();

//                searchResults(text);
            });


        }catch(Exception e){}



//        deleteDoctor();

    }

    //method for deleting doctors
  /*  private void deleteDoctor() {
        doctorList.setOnItemClickListener((parent, view, position, id) -> {

            try {
                DoctorModel clickedDoctor = (DoctorModel) parent.getItemAtPosition(position);

                databaseManager.deleteDoctor(clickedDoctor);

            }catch(Exception e){

            }

        });
    }*/


    public void displayDoctorInfo() {
//        try {
            recyclerView = findViewById(R.id.doctor_recyclerview);
            DoctorAdapter doctorAdapter = new DoctorAdapter(AvailableDoctors.this, doctorNames, doctorNumber, doctorEmail);

            recyclerView.setAdapter(doctorAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(AvailableDoctors.this));
//        } catch (Exception e){}
    }

    //setting up recyclerView

    private  void gettingDoctors(){
        doctorNames.add(databaseManager.getDoctorName().toString());
        doctorNumber.add(databaseManager.getDoctorName().toString());
        doctorEmail.add(databaseManager.getDoctorName().toString());

        displayDoctorInfo();
    }

  /*  public void searchResults(String text){
        databaseManager = new DatabaseManager(AvailableDoctors.this);

        List<String> searchedDoctors = databaseManager.getDoctorSearched(text);

        ArrayAdapter searchedDoctorAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1 , searchedDoctors);
        doctorList.setAdapter(searchedDoctorAdapter);

    }
*/

    public int getDoctorCount(){

        return listDoctors.size();
    }
}