package com.jwhh.stiawareness;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.jwhh.stiawareness.databinding.ActivityAvailableDoctorsBinding;

import java.util.ArrayList;
import java.util.List;

public class AvailableDoctors extends AppCompatActivity {
    private ActivityAvailableDoctorsBinding binding;

    private ListView doctorList;
    private ArrayList<DoctorModel> listDoctors;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ImageView searchDoctorName;
    private EditText searchText;
//    private ArrayList<String> doctorNames = new ArrayList<>();
//    private ArrayList<String> doctorEmail = new ArrayList<>();
//    private ArrayList<String> doctorNumber = new ArrayList<>();

    private DatabaseManager databaseManager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAvailableDoctorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        try{
            searchDoctorName = findViewById(R.id.search_doctor);
            searchText = findViewById(R.id.search_doctor_text);

            doctorList = findViewById(R.id.doctor_list_view);

            displayDoctorInfo();

            searchDoctorName.setOnClickListener(v -> {
                String text = searchText.getText().toString();

//                searchResults(text);
            });


        }catch(Exception e){}



        deleteDoctor();

    }

    //method for deleting doctors
    private void deleteDoctor() {
        doctorList.setOnItemClickListener((parent, view, position, id) -> {

            try {
                DoctorModel clickedDoctor = (DoctorModel) parent.getItemAtPosition(position);

                databaseManager.deleteDoctor(clickedDoctor);

            }catch(Exception e){

            }

        });
    }


    public void displayDoctorInfo() {
        try {
            databaseManager = new DatabaseManager(AvailableDoctors.this);

            List<DoctorModel> allDoctors = databaseManager.getDoctorDetails();
            ArrayAdapter doctorArrayAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1 ,allDoctors);

            doctorList.setAdapter( doctorArrayAdapter);
        } catch (Exception e){}
    }

    //setting up recyclerView

/*    private  void gettingDoctors(){
//        doctorNames.add(databaseManager.getDoctorName().toString());
//        doctorNumber.add(databaseManager.getDoctorName().toString());
//        doctorEmail.add(databaseManager.getDoctorName().toString());


    }*/

  /*  public void searchResults(String text){
        databaseManager = new DatabaseManager(AvailableDoctors.this);

        List<String> searchedDoctors = databaseManager.getDoctorSearched(text);

        ArrayAdapter searchedDoctorAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1 , searchedDoctors);
        doctorList.setAdapter(searchedDoctorAdapter);

    }
*/

    public int getDoctorCount(){

        return doctorList.getCount();
    }
}