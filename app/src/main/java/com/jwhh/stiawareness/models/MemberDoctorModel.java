package com.jwhh.stiawareness.models;

public class MemberDoctorModel {
    private String docName, spaceName;
    private int phoneNumber;

    public MemberDoctorModel(String docName, String spaceName, int phoneNumber) {
        this.docName = docName;
        this.spaceName = spaceName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "MemberDoctorModel{" +
                "docName='" + docName + '\'' +
                ", spaceName='" + spaceName + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

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
}
