package com.example.puzzlealarm;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    public void OnToggleClicked(View view) {
        ToggleButton toggleButton = (ToggleButton) view;

        if (toggleButton.isChecked()) {
            // Get selected time from TimePicker
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();

            // Schedule the alarm
            scheduleAlarm(hour, minute);
            Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();
        } else {
            // Cancel the alarm
            cancelAlarm();
            Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private void scheduleAlarm(int hour, int minute) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE);

        // Set the alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        // Log to verify the time
        Log.d("Alarm", "Alarm scheduled for: " + calendar.getTime());

        // Use the set() method for alarms, which is available on all Android versions
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


    private void cancelAlarm() {
        if (alarmManager != null && pendingIntent != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
