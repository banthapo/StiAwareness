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


    public DatabaseManager(@Nullable Context context ) {
        super(context, "member_doctor.db", null, 1);

    }

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MEMBER_TABLE);
        db.execSQL(" DROP TABLE IF EXISTS " + DOCTOR_TABLE);

        onCreate(db);
    }

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

    public boolean addMember(MemberModel memberModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SPACENAME, memberModel.getSpaceName());
        cv.put(PHONE_NUMBER, memberModel.getPhoneNumber());
        cv.put(PASSWORD, memberModel.getPassword());

        long insert = db.insert(MEMBER_TABLE, null, cv);

        return insert != -1;

    }
    public boolean updateDoctor(String title, String fName, String surname, String email, int phoneNum, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        queryString = " UPDATE " + DOCTOR_TABLE + " SET " + DOCTOR_TITLE + " = ? , " + DOCTOR_FIRST_NAME + " = ? , " + DOCTOR_SURNAME + " = ? , " +
        DOCTOR_EMAIL_ADDRESS + " = ?, " + DOCTOR_NAME +" = ? " + " WHERE " + DOCTOR_PHONE_NUMBER + " = " + phoneNum;

        String phoneNumber = String.valueOf(phoneNum);
        Cursor cursor = db.rawQuery(queryString, new String[] {title, fName, surname, email, name});

        if(cursor.moveToFirst()){
            return true;
        } else{
            return false;
        }
    }

    public boolean deleteDoctor(int pNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        queryString = " DELETE FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_PHONE_NUMBER + " = ? ";

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(pNumber)});

        if (cursor.moveToLast()){
            return true;
        } else{
            cursor.close();
            db.close();
            return false;
        }

    }

    public boolean deleteMember(int phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

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

    public  List<String> memberDoctors(){
        SQLiteDatabase db = this.getReadableDatabase();
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

    public int getPhoneNumber(String name) {
        int phoneNumber;

        queryString = "SELECT " + PHONE_NUMBER + " FROM " + MEMBER_TABLE + " WHERE " +
                SPACENAME + " = ? ";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db. rawQuery(queryString, new String[]{name});

        cursor.moveToFirst();
        int number = cursor.getInt(0);
        phoneNumber = number;

        cursor.close();
        db.close();

        return phoneNumber;

    }

    public ArrayList<String> getDoctorNames() {
        SQLiteDatabase db = this.getReadableDatabase();
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

    public String getDoctorName(int number){
        SQLiteDatabase db = this.getReadableDatabase();
        String doctorName;

        queryString = "SELECT " + DOCTOR_NAME + " FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_PHONE_NUMBER + " = ? ";

        Cursor cursor = db.rawQuery(queryString, new String[] {String.valueOf(number)});

        cursor.moveToFirst();
            doctorName = cursor.getString(0);

            cursor.close();
            db.close();

        return doctorName;
    }


    public ArrayList<String> getDoctorEmail(){
        SQLiteDatabase db = this.getReadableDatabase();
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

    public ArrayList<Integer> getDoctorPhoneNumber(){
        SQLiteDatabase db = this.getReadableDatabase();
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

    public boolean checkSpaceName(String spaceName) {
        SQLiteDatabase db = this.getReadableDatabase();

        queryString = " SELECT * FROM " + MEMBER_TABLE + " WHERE " + SPACENAME + " = ?";

        Cursor cursor = db.rawQuery(queryString, new String[]{spaceName});

        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkLogDetails(String spaceName, String password){
        SQLiteDatabase db = this.getReadableDatabase();

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