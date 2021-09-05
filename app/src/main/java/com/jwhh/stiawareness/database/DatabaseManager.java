package com.jwhh.stiawareness.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.jwhh.stiawareness.models.DoctorModel;
import com.jwhh.stiawareness.models.MemberModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    //declaring global values
    public static final String DOCTOR_TABLE = "DOCTOR_TABLE";
    public static final String DOCTOR_TITLE = "DOCTOR_TITLE";
    public static final String DOCTOR_FIRST_NAME = "DOCTOR_FIRST_NAME";
    public static final String DOCTOR_SURNAME = "DOCTOR_SURNAME";
    public static final String DOCTOR_PHONE_NUMBER = "DOCTOR_PHONE_NUMBER";
    public static final String DOCTOR_EMAIL_ADDRESS = "DOCTOR_EMAIL_ADDRESS";

    public static final String MEMBER_TABLE = "MEMBER_TABLE";
    public static final String SPACENAME = "SPACENAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String PASSWORD = "PASSWORD";
    public static final String DOCTOR_NAME = "DOCTOR_NAME";
    private String queryString;
    private SQLiteDatabase db;

    // ends here

    //creating constructor for database objects
    public DatabaseManager(@Nullable Context context ) {
        super(context, "member_doctor.db", null, 1);

    }

    //creating tables in database
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String doctorStatement = "CREATE TABLE " + DOCTOR_TABLE + " (" + DOCTOR_TITLE + " TEXT , " + DOCTOR_FIRST_NAME + " TEXT , " +
                    DOCTOR_SURNAME + " TEXT, " + DOCTOR_PHONE_NUMBER + " INTEGER PRIMARY KEY , " + DOCTOR_EMAIL_ADDRESS + " TEXT UNIQUE , " + DOCTOR_NAME +
                    " TEXT )";

            String memberStatement = "CREATE TABLE " + MEMBER_TABLE + " ( " + SPACENAME + " TEXT UNIQUE , " + PHONE_NUMBER +
                    " INT PRIMARY KEY , " + PASSWORD + " TEXT)";


            db.execSQL(doctorStatement);
            db.execSQL(memberStatement);
        }catch (Exception e){

        }

    }

    //creating options on database upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MEMBER_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + DOCTOR_TABLE);

        onCreate(db);
    }

    //adding values in database in doctor table using doctor model and ContentValues
    public boolean addDoctor(DoctorModel doctorModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DOCTOR_TITLE, doctorModel.getTitle());
        cv.put(DOCTOR_FIRST_NAME, doctorModel.getFirstName());
        cv.put(DOCTOR_SURNAME, doctorModel.getSurname());
        cv.put(DOCTOR_PHONE_NUMBER, doctorModel.getPhoneNumber());
        cv.put(DOCTOR_EMAIL_ADDRESS, doctorModel.getEmailAddress());
        cv.put(DOCTOR_NAME, doctorModel.getName());

        long insert = db.insert(DOCTOR_TABLE, null, cv);

        return insert != -1;

    }

    //adding values in database in members table using member model and ContentValues
    public boolean addMember(MemberModel memberModel){
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SPACENAME, memberModel.getSpaceName());
        cv.put(PHONE_NUMBER, memberModel.getPhoneNumber());
        cv.put(PASSWORD, memberModel.getPassword());

        long insert = db.insert(MEMBER_TABLE, null, cv);

        return insert != -1;

    }

    //updating values in doctor table
    public boolean updateDoctor(String title, String fName, String surname, String email,  String name, int phoneNum, int phoneNumber){
        db = this.getWritableDatabase();

        queryString = " UPDATE " + DOCTOR_TABLE + " SET " + DOCTOR_TITLE + " = ? , " + DOCTOR_FIRST_NAME + " = ? , " + DOCTOR_SURNAME + " = ? , " +
        DOCTOR_EMAIL_ADDRESS + " = ?, " + DOCTOR_NAME +" = ?, "+ DOCTOR_PHONE_NUMBER + " = ? " + " WHERE " + DOCTOR_PHONE_NUMBER + " = " + phoneNum;



        String docNum = String.valueOf(phoneNumber);
        Cursor cursor = db.rawQuery(queryString, new String[] {title, fName, surname, email, name, docNum});

        if(cursor.moveToFirst()){
            return true;
        } else{
            return false;
        }
    }

    //changing phone number in member table to sych when updating doctor values being the joining part for the tables
    public boolean setMemberPhoneNumber(int oldNumber, int newNumber){
        db = this.getWritableDatabase();

        queryString = " UPDATE " + MEMBER_TABLE + " SET " + PHONE_NUMBER + " = ? " + " WHERE " + PHONE_NUMBER + " = " + oldNumber;

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(newNumber)});

        if (cursor.moveToFirst()){
            return true;
        } else{
            cursor.close();
            db.close();
            return false;
        }
    }

    //removing doctor form doctor table
    public boolean deleteDoctor(int pNumber) {
        db = this.getWritableDatabase();

        queryString = " DELETE FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_PHONE_NUMBER + " = ? ";

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(pNumber)});

        if (cursor.moveToFirst()){
            return true;
        } else{
            cursor.close();
            db.close();
            return false;
        }

    }

    //removing member from member table
    public boolean deleteMember(int phoneNumber) {
        db = this.getWritableDatabase();

        queryString = " DELETE FROM " + MEMBER_TABLE+ " WHERE " + PHONE_NUMBER + " = ? ";

        Cursor cursor = db.rawQuery(queryString , new String[] {String.valueOf(phoneNumber)});

        if (cursor.moveToFirst()){
            return true;
        } else{
            cursor.close();
            db.close();
            return false;
        }
    }

    //getting phone number of member using spacename
    public int getPhoneNumber(String name) {
        db = this.getReadableDatabase();
        int phoneNumber;

        queryString = "SELECT " + PHONE_NUMBER + " FROM " + MEMBER_TABLE + " WHERE " + SPACENAME + " = ? ";

        Cursor cursor = db. rawQuery(queryString, new String[]{name});

        cursor.moveToFirst();
        int number = cursor.getInt(0);
        phoneNumber = number;

        cursor.close();
        db.close();

        return phoneNumber;

    }


    //getting the name of doctor using phone number in doctor table
    public String getDoctorName(int number){
        db = this.getReadableDatabase();
        String doctorName;

        queryString = "SELECT " + DOCTOR_NAME + " FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_PHONE_NUMBER + " = ? ";

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(number)});

        cursor.moveToFirst();
            doctorName = cursor.getString(0);

            cursor.close();
            db.close();

        return doctorName;
    }

    //getting a spacename list of doctors in database
    public List<String> memberDoctors(){
        db = this.getReadableDatabase();
        List<String> returnMemberDoctors = new ArrayList<>();

        queryString = " SELECT " + SPACENAME + " FROM " + MEMBER_TABLE + ", " + DOCTOR_TABLE + " WHERE " + DOCTOR_PHONE_NUMBER + " = " + PHONE_NUMBER;

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                String spaceName = cursor.getString(0);

                returnMemberDoctors.add(spaceName);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnMemberDoctors;
    }

    //getting list of doctor names in doctor table
    public ArrayList<String> getDoctorNames() {
        db = this.getReadableDatabase();
        ArrayList<String> returnDoctors = new ArrayList<>();

        queryString = "SELECT " + DOCTOR_NAME + " FROM " + DOCTOR_TABLE;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                String name = cursor.getString(0);
                returnDoctors.add( name);

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return returnDoctors;
    }

    //getting list of doctor emails
    public ArrayList<String> getDoctorEmail(){
        db = this.getReadableDatabase();
        ArrayList<String> returnEmailAddresses = new ArrayList<>();

        queryString = "SELECT " + DOCTOR_EMAIL_ADDRESS + " FROM " + DOCTOR_TABLE;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                String email = cursor.getString(0);
                returnEmailAddresses.add( email);

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return returnEmailAddresses;
    }

    //getting list of phone numbers for doctors
    public ArrayList<Integer> getDoctorPhoneNumber(){
        db = this.getReadableDatabase();
        ArrayList<Integer> returnPhoneNumber = new ArrayList<>();

        queryString = "SELECT " + DOCTOR_PHONE_NUMBER+ " FROM " + DOCTOR_TABLE;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                int phoneNumber = cursor.getInt(0);

                returnPhoneNumber.add(phoneNumber);

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return returnPhoneNumber;
    }

    //checking the validity of space name
    public boolean checkSpaceName(String spaceName) {
        db = this.getReadableDatabase();

        queryString = " SELECT * FROM " + MEMBER_TABLE + " WHERE " + SPACENAME + " = ?";

        Cursor cursor = db.rawQuery(queryString, new String[]{spaceName});

        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }

    }

    //checking log details for authentication
    public boolean checkLogDetails(String spaceName, String password){
        db = this.getReadableDatabase();

        queryString =" SELECT * FROM " + MEMBER_TABLE + " WHERE " + SPACENAME + " = ? AND " +
                PASSWORD + " = ? ";

        Cursor cursor = db.rawQuery(queryString, new String[] {spaceName, password});

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }

    }

}