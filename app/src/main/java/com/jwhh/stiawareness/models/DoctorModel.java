package com.jwhh.stiawareness.models;

public class DoctorModel {

    //declaring field variables
    private String title, firstName, surname, emailAddress,name;
    private int phoneNumber;

    public DoctorModel(String title, String firstName, String surname, int phoneNumber, String emailAddress, String name) {
        this.title = title;
        this.firstName = firstName;
        this.surname = surname;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    //setting up String values for the doctor model
    @Override
    public String toString() {
        return "DoctorModel{" +
                "title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    //setting getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getName(){
        return  name;
    }

}
