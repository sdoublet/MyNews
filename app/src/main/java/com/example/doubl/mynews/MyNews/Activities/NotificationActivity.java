package com.example.doubl.mynews.MyNews.Activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.doubl.mynews.MyNews.Utils.AlertReceiver;
import com.example.doubl.mynews.R;

import java.util.Calendar;


public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    Switch mySwitch = null;

    public static final int NOTIFICATION_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mySwitch = findViewById(R.id.switch_notification);


        this.configureToolbar();
        this.setSwitchNotification();

    }

    private void configureToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    private void setSwitchNotification() {

        final SharedPreferences sharedPreferences = getSharedPreferences("isChecked", 0);
        boolean value = false;
        value = sharedPreferences.getBoolean("isChecked", value);
        mySwitch.setChecked(value);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {

                    sharedPreferences.edit().putBoolean("isChecked", true).apply();
                    Toast.makeText(getApplicationContext(), "You will receive one notification by day ", Toast.LENGTH_SHORT).show();
                    sendNotification(getApplicationContext());
                    //send daily notification
                    setCalendarTime();
                } else
                    sharedPreferences.edit().putBoolean("isChecked", false).apply();
                Toast.makeText(getApplicationContext(), "Notification disable", Toast.LENGTH_SHORT).show();
                cancelAlarm();

            }
        });

    }

    public void sendNotification(Context context) {

        //set notification's tap action
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //set notification content
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder.setContentTitle("MyNews")
                .setContentText("hello every body")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        createNotificationChannel();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build());

    }

    public void createNotificationChannel() {
        //only for Oreo version and sup
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void setCalendarTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 30);


        startAlarm(calendar);
    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, 0);

        alarmManager.cancel(pendingIntent);
    }


}
