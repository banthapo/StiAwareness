package com.jwhh.stiawareness;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jwhh.stiawareness.databinding.ActivityAvailableDoctorsBinding;

import java.util.List;

public class AvailableDoctors extends AppCompatActivity {
    private ActivityAvailableDoctorsBinding binding;

    private ListView doctorList;
    private String doctorDetails;
    private ImageView backButton;
    private String searchResults;

//    private ArrayList<DoctorModel> listDoctors;

//    private RecyclerView recyclerView;
//    private RecyclerView.LayoutManager layoutManager;

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



            searchDoctorName = findViewById(R.id.search_doctor);
            searchText = findViewById(R.id.search_doctor_text);

            doctorList = findViewById(R.id.doctor_list_view);
            backButton = findViewById(R.id.from_available_doctors);

        displayDoctorName();

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(AvailableDoctors.this, Awareness.class);
            startActivity(intent);
        });
    try {
        doctorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                delete(parent, position);

//                displayDoctorName();
            }
        });

    } catch (Exception e){}

    searchDoctorName.setOnClickListener(new View.OnClickListener() {
        private DatabaseManager databaseManager;

        @Override
        public void onClick(View v) {
            try {
                databaseManager = new DatabaseManager(AvailableDoctors.this);
                String search = searchText.getText().toString();

                List<String> searchedDoctor = this.databaseManager.getDoctorSearched(search);
                ArrayAdapter doctorArrayAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1, searchedDoctor);

                if (search == "") {
                    displayDoctorName();
                    return;
                } else {
                    doctorList.setAdapter(doctorArrayAdapter);
                }
            } catch (Exception e){
            }
        }
    });

    }

    //displaying doctor names to listview
    public void doctorDetails() {
            databaseManager = new DatabaseManager(AvailableDoctors.this);

            List<DoctorModel> allDoctors = this.databaseManager.getDoctorDetails();
            ArrayAdapter doctorArrayAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1 , allDoctors);

            doctorDetails = doctorArrayAdapter.toString();

    }


    //method for deleting doctors
    private void delete(AdapterView<?> parent, int position) {
        try{
            DoctorModel clickedDoctor = (DoctorModel) parent.getItemAtPosition(position);
            databaseManager.deleteDoctor(clickedDoctor);

            Toast.makeText(AvailableDoctors.this, "Successfully deleted" + clickedDoctor, Toast.LENGTH_LONG).show();
            displayDoctorName();
        }catch(Exception e){
            Toast.makeText(AvailableDoctors.this, "Delete failed", Toast.LENGTH_LONG).show();
        }
    }

    public void displayDoctorName(){
        databaseManager = new DatabaseManager(AvailableDoctors.this);

        List<String> allDoctors = databaseManager.getDoctorName();
        ArrayAdapter doctorArrayAdapter = new ArrayAdapter(AvailableDoctors.this, android.R.layout.simple_list_item_1 , allDoctors);

        doctorList.setAdapter( doctorArrayAdapter);
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

/*
    public int getDoctorCount(){

        return doctorList.getCount();
    }

 */
}
