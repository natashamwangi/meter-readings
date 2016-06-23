package com.nancy.loginregister.models;

import java.util.ArrayList;

/**
 * Created by Nancy on 05/25/2016.
 */
public class Readings {

    private String accno, thumbnailUrl;
    private int year,meterno;
    private ArrayList<String> message;

public  Readings() {


}
    public  Readings(String account, String thumbnailUrl, int year, int meterno, ArrayList<String> message) {

        this.accno = account;
        this.thumbnailUrl = thumbnailUrl;
        this.year = year;
        this.meterno = meterno;
        this.message = message;
    }

    public String getAccount() {
        return accno;
    }

    public void setAccount(String name) {
        this.accno = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMeter() {
        return meterno;
    }

    public void setMeter(int meterno) {
        this.meterno = meterno;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }
    }

