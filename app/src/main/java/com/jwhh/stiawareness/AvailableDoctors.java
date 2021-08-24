package com.jwhh.stiawareness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.jwhh.stiawareness.databinding.ActivityAvailableDoctorsBinding;

import java.util.List;

public class AvailableDoctors extends AppCompatActivity {
    private ActivityAvailableDoctorsBinding binding;

    private ListView doctorList;
    private String doctorDetails;
    private ImageView backButton;
    private String searchResults;


    private ImageView searchDoctorName;
    private EditText searchText;

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAvailableDoctorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchDoctorName = findViewById(R.id.search_doctor);
        searchText = findViewById(R.id.search_doctor_text);

        doctorList = findViewById(R.id.doctor_list_view);
        backButton = findViewById(R.id.from_available_doctors);

        displayDoctorName();

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AvailableDoctors.this, Awareness.class);
            startActivity(intent);
        });

        doctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            try {
                String clickedDoctor = (String) parent.getItemAtPosition(position);
                databaseManager.deleteDoctor(clickedDoctor);

                displayDoctorName();
//            } catch (Exception e) {
//                Toast.makeText(AvailableDoctors.this, "Delete failed", Toast.LENGTH_LONG).show();
//            }
            }
        });

        searchDoctor();
    }

    private void searchDoctor() {
        searchDoctorName.setOnClickListener(new View.OnClickListener() {
            private DatabaseManager databaseManager;

            @Override
            public void onClick(View v) {
                try {
                    databaseManager = new DatabaseManager(AvailableDoctors.this);
                    String search = searchText.getText().toString();

                    List<String> searchedDoctor = this.databaseManager.getDoctorSearched(search);
                    ArrayAdapter doctorArrayAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1, searchedDoctor);

                    doctorList.setAdapter(doctorArrayAdapter);

                } catch (Exception e){
                    displayDoctorName();
                }
            }
        });
    }



    public void displayDoctorName(){
        try {
            databaseManager = new DatabaseManager(AvailableDoctors.this);

            List<String> allDoctors = databaseManager.getDoctorName();
            ArrayAdapter doctorArrayAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1 , allDoctors);

            doctorList.setAdapter( doctorArrayAdapter);
        } catch (Exception e){}

    }

        //getting doctors full details
      /*  public void displayDoctorInfo() {
            try {
                doctorList.setOnItemLongClickListener((parent, view, position, id) -> {

                    DoctorModel clickedDoctor = (DoctorModel) parent.getItemAtPosition(position);
                    String doctorDetails =  databaseManager.getDoctorDetails();

                    if (!deleteDoctor()) {
                        Toast.makeText(AvailableDoctors.this, doctorDetails, Toast.LENGTH_LONG).show();
                        return true;
                    } else {
                        return  false;
                    }
                });
            }catch(Exception e){

            }

        }*/

    //displaying doctor names to listview
       /* public void doctorDetails() {
                databaseManager = new DatabaseManager(AvailableDoctors.this);

                List<DoctorModel> allDoctors = this.databaseManager.getDoctorDetails();
                ArrayAdapter doctorArrayAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1 , allDoctors);

                doctorDetails = doctorArrayAdapter.toString();

        }*/


    //method for deleting doctors
       /* private void delete(AdapterView<?> parent, int position) {

                Toast.makeText(AvailableDoctors.this, "Successfully deleted" + clickedDoctor, Toast.LENGTH_LONG).show();
        }*/


        public int getDoctorCount(){

            return doctorList.getCount();
        }

}
