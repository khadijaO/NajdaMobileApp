package com.example.najdaapp.Message;

public class MessageModule {

    private int id;
    private String salutation;
    private String body;
    private boolean receiver;
    private boolean gps;


    public MessageModule(String salutation, String body, boolean receiver, boolean gps) {
        this.salutation = salutation;
        this.body = body;
        this.receiver = receiver;
        this.gps = gps;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isReceiver() {
        return receiver;
    }

    public void setReceiver(boolean receiver) {
        this.receiver = receiver;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
    }
}