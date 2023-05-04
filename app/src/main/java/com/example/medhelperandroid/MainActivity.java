package com.example.medhelperandroid;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medhelperandroid.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner mProfileSpinner;
    private List<String> mProfileList;

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

        // Обработчик выбора элемента в спиннере
        mProfileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProfile = mProfileList.get(position);
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
                // Здесь можно добавить код для открытия экрана добавления нового профиля
                Toast.makeText(MainActivity.this, "Добавление нового профиля", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
