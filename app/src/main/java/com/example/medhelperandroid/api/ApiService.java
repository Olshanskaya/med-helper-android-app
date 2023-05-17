package com.example.medhelperandroid.api;

import com.example.medhelperandroid.api.patient.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("user/patient/{id}")
    Call<List<Patient>> getPatient(@Path("id") long id);
}

