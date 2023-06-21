package com.example.medhelperandroid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medhelperandroid.api.ApiService;
import com.example.medhelperandroid.api.meds.Medicine;
import com.example.medhelperandroid.api.patient.Patient;
import com.example.medhelperandroid.notification.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMedicineActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    ApiService apiService = retrofit.create(ApiService.class);

    private TextView patientName;
    private EditText dateInput;
    private AutoCompleteTextView medicineSearch;
    private Button addButton, cancelButton;

    private List<Medicine> meds;

    private ArrayList<String> medicines = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String patientName1 = intent.getStringExtra("patientName");


        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicine_activity);

        patientName = findViewById(R.id.patient_name);
        medicineSearch = findViewById(R.id.medicine_search);
        addButton = findViewById(R.id.add_button);
        cancelButton = findViewById(R.id.cancel_button);
        dateInput = findViewById(R.id.date_input);

        String patientNameStr = patientName1;
        patientName.setText(patientNameStr);


        medicines.add("med1");
        medicines.add("med2");
        medicines.add("med3");

        //------------------------------------------------------------------------------------------
        // Set OnClickListener to open DatePickerDialog when dateInput is clicked
        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddMedicineActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dateInput.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        //-------------------------------------------------------------------------------------------

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                medicines
        );
        medicineSearch.setAdapter(adapter);

        Call<List<Medicine>> call = apiService.getMedicine();
        call.enqueue(new Callback<List<Medicine>>() {
            @Override
            public void onResponse(Call<List<Medicine>> call, Response<List<Medicine>> response) {
                if (response.isSuccessful()) {
                    meds = response.body();
                    if (meds != null && !meds.isEmpty()) {
                        medicines.clear();
                        meds.forEach(a->medicines.add(a.getName()));
                    }

                    adapter.notifyDataSetChanged();
                } else {
                    int i = 1 + 2;
                }
            }

            @Override
            public void onFailure(Call<List<Medicine>> call, Throwable t) {
                int i = 1 + 2;
            }
        });





        //--------------------------------------------------------------------------------------------



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // код для выполнения при нажатии на кнопку "Add"
                // вы можете добавить лекарство здесь
                String medicineName = medicineSearch.getText().toString();
                // условие в зависимости от параметра в коде
                boolean condition = true; // замените это на ваше реальное условие
                String message;
                if (condition) {
                    message = "Это лекарство не совместимо с одним из Ваших. Всё равно добавить?";
                } else {
                    message = "Это лекарство не конфликтует с уже принимаемыми Вами. Продолжить?";
                }

                new AlertDialog.Builder(AddMedicineActivity.this)
                        .setTitle("Add Medicine")
                        .setMessage(message)
                        .setPositiveButton("Add Medicine", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // код для выполнения при нажатии на кнопку "Cancel"
                                // вы можете закрыть диалог здесь
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMedicineActivity.this, MainActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
                finish();
            }
        });



    }
}
