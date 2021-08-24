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
    public static final String SPACENAME = "SPACENAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String PASSWORD = "PASSWORD";
    public static final String DOCTOR_NAME = "DOCTOR_NAME";


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
//        db.execSQL("DROP TABLE IF EXISTS MEMBER_TABLE");

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


    public boolean deleteDoctor(String name) {
        DoctorModel doctorModel = null;
        boolean name1 = doctorModel.getName().equals(name);
        String pNumber = null;

        if (name1)
           pNumber = String.valueOf(doctorModel.getPhoneNumber());


        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DELETE FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_PHONE_NUMBER + " = ? ";

        Cursor cursor = db.rawQuery(queryString , new String[] {pNumber});

        if (cursor.moveToFirst()){
            return true;
        } else{
            cursor.close();
            return false;
        }
    }

    public List<String> getDoctorName() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> returnDoctors = new ArrayList<>();

        String queryString = "SELECT " + DOCTOR_NAME + " FROM " + DOCTOR_TABLE;

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

    public List<String> getDoctorSearched(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> returnDoctors = new ArrayList<>();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + DOCTOR_TABLE + " WHERE " + DOCTOR_TITLE + " LIKE ? OR " +
                DOCTOR_FIRST_NAME + " LIKE ? OR " + DOCTOR_SURNAME + " LIKE  ? ", new String[] {"%"+name+"%", "%"+name+"%","%"+name+"%"});

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

   /* public List<DoctorModel> getDoctorDetails(){
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
                String name = cursor.getString(5);

                DoctorModel newDoctor = new DoctorModel(title, firstName, surname, phoneNumber, emailAddress, name);
                returnDoctors.add(newDoctor);

            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnDoctors ;
    }
*/
    public List<MemberModel> getMemberDetails(){
        List<MemberModel> returnMembers = new ArrayList<>();

        String queryString = "SELECT * FROM " + MEMBER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){

            do {
                String spaceName = cursor.getString(0);
                int phoneNumber = cursor.getInt(1);
                String password = cursor.getString(2);

                MemberModel newMember = new MemberModel(spaceName, phoneNumber, password);
                returnMembers.add(newMember);

            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnMembers;
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


    public boolean checkSpaceName(String spaceName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM MEMBER_TABLE WHERE SPACENAME = ?", new String[]{spaceName});

        if (cursor.getCount() > 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkLogDetails(String spaceName, String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(" SELECT * FROM " + MEMBER_TABLE + " WHERE " + SPACENAME + " = ? AND " +
                PASSWORD + " = ? ", new String[] {spaceName, password});

        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }

    }
}