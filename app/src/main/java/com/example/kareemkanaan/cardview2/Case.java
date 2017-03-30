package com.example.kareemkanaan.cardview2;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Property;

import java.util.List;

/**
 * Created by kareemkanaan on 3/4/17.
 */

public class Case implements Parcelable{
    private String str;
    private int img;
    private String location;
    private String description;
    private String needs;
    private String phone;
    private List<Integer> imgs;


    public Case(String str, int img, List<Integer> imgs, String location, String needs, String description,String phone) {
        this.str = str;
        this.img = img;
        this.imgs = imgs;
        this.location = location;
        this.needs = needs;
        this.description = description;
        this.phone=phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public String getStr() {
        return str;
    }

    public int getImg() {
        return img;
    }

    public List<Integer> getImgs() {
        return imgs;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(str);
        dest.writeInt(img);
        dest.writeList(imgs);
        dest.writeString(location);
        dest.writeString(description);
        dest.writeString(needs);
        dest.writeString(phone);
    }

    //constructor used for parcel
    public Case(Parcel parcel) {
        str = parcel.readString();
        img = parcel.readInt();
        imgs = parcel.readArrayList(Integer.class.getClassLoader());
        location = parcel.readString();
        description = parcel.readString();
        needs = parcel.readString();
        phone=parcel.readString();
    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Case> CREATOR = new Parcelable.Creator<Case>() {

        @Override
        public Case createFromParcel(Parcel parcel) {
            return new Case(parcel);
        }

        @Override
        public Case[] newArray(int size) {
            return new Case[0];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }


    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getNeeds() {
        return needs;
    }
}