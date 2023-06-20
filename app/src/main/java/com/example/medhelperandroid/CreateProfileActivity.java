package com.example.medhelperandroid;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medhelperandroid.ui.login.LoginActivity;

import java.util.Calendar;

public class CreateProfileActivity extends AppCompatActivity {

    private EditText nameEditText;
    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private Button cancelButton;
    private Button saveButton;
    private Button breakfastTimeButton;
    private Button lunchTimeButton;
    private Button dinerTimeButton;
    private Button startDayTimeButton;
    private Button endDayTimeButton;

    private TextView breakfastTimeTextView;
    private TextView lunchTimeTextView;
    private TextView dinnerTimeTextView;
    private TextView startDayTimeTextView;
    private TextView endDayTimeTextView;

    private int selectedHour;
    private int selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_profile_activity);
        breakfastTimeTextView = findViewById(R.id.breakfast_time_textview);
        lunchTimeTextView = findViewById(R.id.lunch_time_textview);
        dinnerTimeTextView = findViewById(R.id.dinner_time_textview);
        startDayTimeTextView = findViewById(R.id.start_day_time_textview);
        endDayTimeTextView = findViewById(R.id.end_day_time_textview);

        nameEditText = findViewById(R.id.name_edittext);
        //hourPicker = findViewById(R.id.breakfast_hour_picker);
        //minutePicker = findViewById(R.id.breakfast_minute_picker);
        cancelButton = findViewById(R.id.patient_cancel_button);
        saveButton = findViewById(R.id.patient_save_button);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateProfileActivity.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
            }
        });

        Button saveButton = findViewById(R.id.patient_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                //int hour = hourPicker.getValue();
                //int minute = minutePicker.getValue();

                Intent intent = new Intent(CreateProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        breakfastTimeButton = findViewById(R.id.breakfast_time_button);
        lunchTimeButton = findViewById(R.id.lunch_time_button);
        dinerTimeButton = findViewById(R.id.dinner_time_button);
        startDayTimeButton = findViewById(R.id.start_day_time_button);
        endDayTimeButton = findViewById(R.id.end_day_time_button);


        // Обработчик нажатия на кнопку "Выбрать время"
        breakfastTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(breakfastTimeTextView);
            }
        });

        // Обработчик нажатия на кнопку "Выбрать время"
        lunchTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(lunchTimeTextView);
            }
        });

        // Обработчик нажатия на кнопку "Выбрать время"
        dinerTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(dinnerTimeTextView);
            }
        });

        // Обработчик нажатия на кнопку "Выбрать время"
        startDayTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(startDayTimeTextView);
            }
        });

        // Обработчик нажатия на кнопку "Выбрать время"
        endDayTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(endDayTimeTextView);
            }
        });

    }


    // Метод для отображения TimePickerDialog
    private void showTimePickerDialog(TextView textView) {
        // Получение текущего времени
        final Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Создание объекта TimePickerDialog и установка слушателя выбора времени
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Обновление выбранного времени
                        selectedHour = hourOfDay;
                        selectedMinute = minute;

                        // Обновление текста в TextView
                        String selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                        textView.setText(selectedTime);
                    }
                }, currentHour, currentMinute, true);

        // Отображение диалогового окна выбора времени
        timePickerDialog.show();
    }


}
