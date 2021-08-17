package com.jwhh.stiawareness;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    public static final String DOCTOR_TABLE = "DOCTOR_TABLE";
    public static final String DOCTOR_TITLE = "DOCTOR_TITLE";
    public static final String DOCTOR_FIRST_NAME = "DOCTOR_FIRST_NAME";
    public static final String DOCTOR_SURNAME = "DOCTOR_SURNAME";
    public static final String DOCTOR_PHONE_NUMBER = "DOCTOR_PHONE_NUMBER";
    public static final String DOCTOR_EMAIL_ADDRESS = "DOCTOR_EMAIL_ADDRESS";

    public static final String MEMBER_TABLE = "MEMBER_TABLE";
    public static final String SPACE_NAME = "SPACE_NAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String PASSWORD = "PASSWORD";
    public static final String CONFIRM_PASSWORD = "CONFIRM_PASSWORD";


    public DatabaseManager(@Nullable Context context ) {
        super(context, "member_doctor.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String doctorStatement = "CREATE TABLE " + DOCTOR_TABLE + " (" + DOCTOR_TITLE + " TEXT, " + DOCTOR_FIRST_NAME + " TEXT, " +
                    DOCTOR_SURNAME + " TEXT, " + DOCTOR_PHONE_NUMBER + " INTEGER PRIMARY KEY, " + DOCTOR_EMAIL_ADDRESS + " TEXT)";

            String memberStatement = "CREATE TABLE " + MEMBER_TABLE + " ( " + SPACE_NAME + " TEXT UNIQUE, " + PHONE_NUMBER +
                    " INT PRIMARY KEY , " + PASSWORD + " TEXT , " + CONFIRM_PASSWORD + " TEXT )";


            db.execSQL(doctorStatement);
            db.execSQL(memberStatement);
        }catch (Exception e){

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addDoctor(DoctorModel doctorModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(DOCTOR_TITLE, doctorModel.getTitle());
        cv.put(DOCTOR_FIRST_NAME, doctorModel.getFirstName());
        cv.put(DOCTOR_SURNAME, doctorModel.getSurname());
        cv.put(DOCTOR_PHONE_NUMBER, doctorModel.getPhoneNumber());
        cv.put(DOCTOR_EMAIL_ADDRESS, doctorModel.getEmailAddress());

        long insert = db.insert(DOCTOR_TABLE, null, cv);

        return insert != -1;

    }

    public boolean addMember(MemberModel memberModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SPACE_NAME, memberModel.getSpaceName());
        cv.put(PHONE_NUMBER, memberModel.getPhoneNumber());
        cv.put(PASSWORD, memberModel.getPassword());
        cv.put(CONFIRM_PASSWORD, memberModel.getConfirmPassword());

        long insert = db.insert(MEMBER_TABLE, null, cv);

        return insert != -1;

    }


    public boolean deleteDoctor(DoctorModel doctorModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DELETE FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_PHONE_NUMBER + " = " +
                doctorModel.getPhoneNumber();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        } else{
            cursor.close();
            return false;
        }
    }

    public List<DoctorModel> getDoctorDetails(){
        List<DoctorModel> returnDoctors = new ArrayList<>();

        String queryString = "SELECT * FROM " + DOCTOR_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){

            do {
                String title = cursor.getString(0);
                String firstName = cursor.getString(1);
                String surname = cursor.getString(2);
                int phoneNumber = cursor.getInt(3);
                String emailAddress = cursor.getString(4);

                DoctorModel newDoctor = new DoctorModel(title, firstName, surname, phoneNumber, emailAddress);
                returnDoctors.add(newDoctor);

            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnDoctors ;
    }

    public List<String> getDoctorName() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> returnDoctors = new ArrayList<>();

        String queryString = "SELECT " + DOCTOR_TITLE + " , " + DOCTOR_FIRST_NAME + " , " +
                DOCTOR_SURNAME + " FROM " + DOCTOR_TABLE;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                String title = cursor.getString(0);
                String firstName = cursor.getString(1);
                String surname = cursor.getString(2);

                String name = title + " " + firstName + " " + surname;
                returnDoctors.add( name );

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return returnDoctors;
    }

    public List<String> getDoctorNumber(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> returnPhoneNumbers = new ArrayList<>();

        String queryString = "SELECT " + DOCTOR_PHONE_NUMBER + " FROM " + DOCTOR_TABLE;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                String pNumber = cursor.getString(3);
                returnPhoneNumbers.add( pNumber);

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return returnPhoneNumbers;
    }

    public List<String> getDoctorEmail(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> returnEmailAddresses = new ArrayList<>();

        String queryString = "SELECT " + DOCTOR_EMAIL_ADDRESS + " FROM " + DOCTOR_TABLE;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                String email = cursor.getString(4);
                returnEmailAddresses.add( email);

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return returnEmailAddresses;
    }

    public List<String> getDoctorSearched(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> returnDoctors = new ArrayList<>();

        String queryString = "SELECT " + DOCTOR_TITLE + " ," + DOCTOR_FIRST_NAME + " ," + DOCTOR_SURNAME + " WHERE " + DOCTOR_TITLE + " LIKE " + name + " OR " +
                DOCTOR_FIRST_NAME + " LIKE " + name + " OR " + DOCTOR_SURNAME + " LIKE " + name;

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                String title = cursor.getString(0);
                String firstName = cursor.getString(1);
                String surname = cursor.getString(2);

                String fullName = title + " " + firstName+ " " + surname;

                returnDoctors.add( fullName );

            } while (cursor.moveToNext());

        }
        cursor.close();
        db.close();

        return returnDoctors;
    }

}