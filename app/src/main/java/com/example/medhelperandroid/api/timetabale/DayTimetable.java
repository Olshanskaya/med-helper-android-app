package com.example.medhelperandroid.api.timetabale;

import com.example.medhelperandroid.api.patient.Patient;
import com.google.gson.annotations.SerializedName;

public class DayTimetable {

    @SerializedName("id")
    private long id;

    @SerializedName("timetable")
    private Timetable timetable;

    @SerializedName("medTime")
    private String medTime;

    @SerializedName("status")
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public String getMedTime() {
        return medTime;
    }

    public void setMedTime(String medTime) {
        this.medTime = medTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
