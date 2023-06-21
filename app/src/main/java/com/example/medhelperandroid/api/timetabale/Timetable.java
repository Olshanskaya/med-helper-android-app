package com.example.medhelperandroid.api.timetabale;

import com.example.medhelperandroid.api.meds.Medicine;
import com.example.medhelperandroid.api.patient.Patient;
import com.google.gson.annotations.SerializedName;

public class Timetable {

    @SerializedName("id")
    private long id;

    @SerializedName("patient")
    private Patient patient;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("endTime")
    private String endTime;

    @SerializedName("dayDuration")
    private String dayDuration;

    @SerializedName("timePerDay")
    private String timePerDay;

    @SerializedName("medicine")
    private Medicine medicine;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDayDuration() {
        return dayDuration;
    }

    public void setDayDuration(String dayDuration) {
        this.dayDuration = dayDuration;
    }

    public String getTimePerDay() {
        return timePerDay;
    }

    public void setTimePerDay(String timePerDay) {
        this.timePerDay = timePerDay;
    }


    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}
