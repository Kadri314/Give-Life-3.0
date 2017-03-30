package com.example.kareemkanaan.cardview2;

/**
 * Created by ASUS on 3/15/2017.
 */

public class CaseData {
    public String Title, PhoneNumber, Location, Description, ImgSource, Needs;

    public CaseData() {
    }

    public CaseData(String title, String phoneNumber, String location, String description, String imgSource, String needs) {
        Title = title;
        PhoneNumber = phoneNumber;
        Location = location;
        Description = description;
        ImgSource = imgSource;
        Needs = needs;
    }
}