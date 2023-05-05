package com.example.medhelperandroid.ui.login;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medhelperandroid.MainActivity;
import com.example.medhelperandroid.R;
import com.example.medhelperandroid.api.ApiService;
import com.example.medhelperandroid.api.LoginRequest;
import com.example.medhelperandroid.api.LoginResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private Button mNewPswButton;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.email_field);
        mPasswordField = findViewById(R.id.password_field);
        mLoginButton = findViewById(R.id.login_button);
        mNewPswButton = findViewById(R.id.new_psw_button);


        mLoginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailField.getText().toString();
                String password = mPasswordField.getText().toString();

                // Здесь можно добавить код для проверки правильности введенной информации пользователя
                LoginRequest loginRequest = new LoginRequest(email, password);
                Call<LoginResponse> call = apiService.login(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String emailReturned = response.body().getEmail();
                            String status = response.body().getStatus();
                            // После успешной проверки, можно запустить следующую активность
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // Опционально закрываем эту активность, чтобы пользователь не мог вернуться к экрану входа по кнопке "Назад"
                            // обработка ответа
                        } else {
                            Toast.makeText(LoginActivity.this, "Введены неверные данные", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Что-то пошло не так, попробуйте позже", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            ;
        });

    }

    ;
}