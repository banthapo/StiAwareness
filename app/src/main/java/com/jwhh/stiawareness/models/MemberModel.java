package com.jwhh.stiawareness.models;

public class MemberModel {
    //declaring field variables
    private String spaceName;
    private int phoneNumber;
    private String password;

    //setting up a constructor for member model
    public MemberModel(String spaceName, int phoneNumber, String password) {
        this.spaceName = spaceName;
        this.phoneNumber = phoneNumber;
        this.password = password;

    }

    //setting up String values for member model
    @Override
    public String toString() {
        return "MemberModel{" +
                "spaceName='" + spaceName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                '}';
    }

    //setting getters and setters
    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
