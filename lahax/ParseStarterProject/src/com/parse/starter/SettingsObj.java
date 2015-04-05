package com.parse.starter;

import com.google.gson.annotations.SerializedName;

/**
 * Created by weird_000 on 4/5/2015.
 */
public class SettingsObj {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("bio")
    private String bio;

    @SerializedName("tutor")
    private boolean tutor;

    @SerializedName("tutor_availability")
    private boolean tutor_availability;

    @SerializedName("tutor_price")
    private float tutor_price;

    public SettingsObj(String email, String password, String name, String bio, boolean tutor, boolean tutor_availability, float tutor_price) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.bio = bio;
        this.tutor = tutor;
        this.tutor_availability = tutor_availability;
        this.tutor_price = tutor_price;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isTutor() {
        return tutor;
    }

    public void setTutor(boolean tutor) {
        this.tutor = tutor;
    }

    public boolean isTutor_availability() {
        return tutor_availability;
    }

    public void setTutor_availability(boolean tutor_availability) {
        this.tutor_availability = tutor_availability;
    }

    public float getTutor_price() {
        return tutor_price;
    }

    public void setTutor_price(float tutor_price) {
        this.tutor_price = tutor_price;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
