package com.example.medhelperandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medhelperandroid.api.ApiService;
import com.example.medhelperandroid.api.patient.Patient;
import com.example.medhelperandroid.api.timetabale.DayTimetable;
import com.example.medhelperandroid.api.timetabale.Timetable;
import com.example.medhelperandroid.elements.MyAdapter;
import com.example.medhelperandroid.notification.AlarmReceiver;
import com.example.medhelperandroid.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private Spinner mProfileSpinner;
    private List<String> mProfileList;
    private String[] myDataset;

    private Button mLogout;
    private String patientName;

    private List<Patient> userList;
    private List<DayTimetable> dayTimetableList;

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
        myDataset = new String[]{"Data 1", "Data 2", "Data 3", "Data 1", "Data 2", "Data 3", "Data 1", "Data 2", "Data 3"};
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // Создаем адаптер для спиннера
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mProfileList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProfileSpinner.setAdapter(adapter);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");
        String[] patientID = new String[1];

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);


        Call<List<Patient>> call = apiService.getPatient(Long.parseLong(userID));



        //-------------------------------------------------------------------------------------------
        // Установить время для срабатывания будильника
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 01);
        calendar.set(Calendar.SECOND, 0);

        // Проверить, если время уже прошло, то добавить один день
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        // Создать интент для будильника
        Intent intentее = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intentее, 0);

        // Установить будильник
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        //-------------------------------------------------------------------------------------------
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

                patientID[0] = patient.getId();
                patientName = patient.getName();

                nameTextView.setText(patient.getName());
                if (patient.getStartDay() != null)
                    dayStartTextView.setText(patient.getStartDay());
                else
                    dayStartTextView.setText("не указано");
                if (patient.getEndDay() != null)
                    endDayTextView.setText(patient.getEndDay());
                else
                    endDayTextView.setText("не указано");
                if (patient.getBreakfast() != null)
                    breakfastTextView.setText(patient.getBreakfast());
                else
                    breakfastTextView.setText("не указано");
                if (patient.getLunch() != null)
                    lunchTextView.setText(patient.getLunch());
                else
                    lunchTextView.setText("не указано");
                if (patient.getDinner() != null)
                    dinerTextView.setText(patient.getDinner());
                else
                    dinerTextView.setText("не указано");
                Toast.makeText(MainActivity.this, "Выбран профиль " + selectedProfile, Toast.LENGTH_SHORT).show();

                //------------------------------------------------------------------------------------------

                if (patientID != null && patientID[0] != null) {
                    Call<List<DayTimetable>> call2 = apiService.getDayTimetable(Long.parseLong(patientID[0]));
                    call2.enqueue(new Callback<List<DayTimetable>>() {
                        @Override
                        public void onResponse(Call<List<DayTimetable>> call2, Response<List<DayTimetable>> response) {
                            if (response.isSuccessful()) {
                                dayTimetableList = response.body();
                                List<String> list = new ArrayList<String>();

                                list.add("Расписание на сегодня: ");
                                if (dayTimetableList.isEmpty()) {
                                    list.add("отсутствует");
                                } else {
                                    dayTimetableList
                                            .forEach(a -> {
                                                    list.add(a.getMedTime() + ": " + a.getTimetable().getMedicine().getName());
                                            });
                                }
                                myDataset = list.toArray(new String[0]);


                                mAdapter = new MyAdapter(myDataset);
                                recyclerView.setAdapter(mAdapter);


                            } else {
                                int i = 1 + 2;
                                // Обработка ошибки
                                // ...
                            }
                        }

                        @Override
                        public void onFailure(Call<List<DayTimetable>> call, Throwable t) {
                            int i = 1 + 2;
                            // Обработка ошибки
                            // ...
                        }
                    });
                }
                //------------------------------------------------------------------------------------------

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
                intent.putExtra("userID", userID);
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

        // Добавляем обработчик нажатия на кнопку "добавить лекарство"
        Button addMedButton = findViewById(R.id.addMedButton);
        addMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // После успешной проверки, можно запустить следующую активность
                Intent intent = new Intent(MainActivity.this, AddMedicineActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("patientName", patientName);
                startActivity(intent);
                finish();
            }
        });

    }
}
