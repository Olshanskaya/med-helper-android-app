package com.example.medhelperandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medhelperandroid.api.ApiService;
import com.example.medhelperandroid.api.patient.Patient;
import com.example.medhelperandroid.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Spinner mProfileSpinner;
    private List<String> mProfileList;

    private Button mLogout;

    private List<Patient> userList;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfileSpinner = findViewById(R.id.profile_spinner);
        mProfileList = new ArrayList<>(Arrays.asList("Пациент1", "Пациент2", "Пациент3"));

        // Создаем адаптер для спиннера
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mProfileList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProfileSpinner.setAdapter(adapter);

        Call<List<Patient>> call = apiService.getPatient(21);
        call.enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(Call<List<Patient>> call, Response<List<Patient>> response) {
                if (response.isSuccessful()) {
                    userList = response.body();
                    mProfileList = userList.stream()
                            .map(c -> c.getName())
                            .collect(Collectors.toList());

                    adapter.clear(); // Clear the existing items in the adapter
                    adapter.addAll(mProfileList); // Add the updated list to the adapter
                    adapter.notifyDataSetChanged(); // Notify the adapter of the data change


                } else {
                    int i = 1 + 2;
                    // Обработка ошибки
                    // ...
                }
            }

            @Override
            public void onFailure(Call<List<Patient>> call, Throwable t) {
                int i = 1 + 2;
                // Обработка ошибки
                // ...
            }
        });



        // Обработчик выбора элемента в спиннере


        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView dayStartTextView = findViewById(R.id.startDayTextView);
        TextView endDayTextView = findViewById(R.id.endDayTextView);
        TextView breakfastTextView = findViewById(R.id.breakfastTextView);
        TextView lunchTextView = findViewById(R.id.lunchTextView);
        TextView dinerTextView = findViewById(R.id.dinerTextView);
        mProfileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProfile = mProfileList.get(position);
                Patient patient = userList.get(position);

                nameTextView.setText(patient.getName());
                if(patient.getStartDay() != null)
                    dayStartTextView.setText(patient.getStartDay());
                if(patient.getEndDay() != null)
                    endDayTextView.setText(patient.getEndDay());
                if(patient.getBreakfast() != null)
                    breakfastTextView.setText(patient.getBreakfast());
                if(patient.getLunch() != null)
                    lunchTextView.setText(patient.getLunch());
                if(patient.getDinner() != null)
                    dinerTextView.setText(patient.getDinner());
                Toast.makeText(MainActivity.this, "Выбран профиль " + selectedProfile, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ничего не делаем
            }
        });

        // Добавляем обработчик нажатия на кнопку "Добавить профиль"
        Button addProfileButton = findViewById(R.id.add_profile_button);
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Добавление нового профиля", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CreateProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // Добавляем обработчик нажатия на кнопку "выход"
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // После успешной проверки, можно запустить следующую активность
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Опционально закрываем эту активность, чтобы пользователь не мог вернуться к экрану входа по кнопке "Назад"
            }
        });
    }
}
