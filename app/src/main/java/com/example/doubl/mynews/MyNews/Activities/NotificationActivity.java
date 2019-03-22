package com.example.doubl.mynews.MyNews.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

import com.example.doubl.mynews.MyNews.Models.ResultSearchApi;
import com.example.doubl.mynews.R;



public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "CHANNEL_ID";
    Switch mySwitch = null;


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
                   // sendNotification();
                    // send notification if a new article appear with criteria at fix date
                    // app must search even if app shut down
                    sharedPreferences.edit().putBoolean("isChecked",true).apply();
                    Toast.makeText(getApplicationContext(), "You will receive one notification by day ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendNotification() {
        ResultSearchApi headLine = new ResultSearchApi();
        //set notification's tap action
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        //set notification content
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        notificationBuilder.setContentTitle("MyNews")
                .setContentText("hello every body")
                .setSmallIcon(R.drawable.alarm_bell)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);

        createNotificationChannel();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notificationBuilder.build());

    }
    private void createNotificationChannel(){
        //only for Oreo version and sup
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel= new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}
