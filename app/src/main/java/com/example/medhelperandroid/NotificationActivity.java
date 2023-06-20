package com.example.medhelperandroid;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Начинаем воспроизведение мелодии
        mediaPlayer = MediaPlayer.create(this, R.raw.your_sound_file); // Укажите свой звуковой файл в папке raw
        mediaPlayer.start();

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Проверяем, есть ли разрешение на вибрацию
        if (vibrator != null && vibrator.hasVibrator()) {
            // Вибрация в течение 500 миллисекунд
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // для API 26 и выше
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // для API ниже 26
                vibrator.vibrate(500);
            }
        }

        TextView notificationText = findViewById(R.id.notification_text);
        notificationText.setText("Пациент: Надежда, Лекарственное средство: Витамин С Эвалар таблетки шипучие 1500 мг");

        Button okButton = findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                finish();
                // Обработка нажатия на кнопку OK
            }
        });

        Button skipButton = findViewById(R.id.skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                finish();
                // Обработка нажатия на кнопку Пропустить
            }
        });
    }
}
