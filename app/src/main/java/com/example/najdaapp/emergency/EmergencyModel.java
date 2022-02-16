package com.example.najdaapp.emergency;

public class EmergencyModel {
    private int id;
    private String phoneNo;
    private String name;

    public EmergencyModel(String phoneNo, String name) {

        this.phoneNo = phoneNo;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
