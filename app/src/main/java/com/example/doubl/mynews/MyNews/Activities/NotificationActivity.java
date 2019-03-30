package com.example.doubl.mynews.MyNews.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doubl.mynews.MyNews.Models.SearchApi;
import com.example.doubl.mynews.MyNews.Utils.AlertReceiver;
import com.example.doubl.mynews.MyNews.Utils.ApiKey;
import com.example.doubl.mynews.MyNews.Utils.NewYorkTimesStream;
import com.example.doubl.mynews.R;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.observers.DisposableObserver;


public class NotificationActivity extends AppCompatActivity {


    Switch mySwitch = null;
    @BindView(R.id.notification_query)
    EditText notificationQuery;
    @BindView(R.id.notification_arts)
    CheckBox notificatonCheckBoxArts;
    @BindView(R.id.notification_business)
    CheckBox notificatonCheckBoxBusinesss;
    @BindView(R.id.notification_entrepreneurs)
    CheckBox notificatonCheckEntrepreneurss;
    @BindView(R.id.notification_politics)
    CheckBox notificatonCheckBoxPolitics;
    @BindView(R.id.notification_sports)
    CheckBox notificatonCheckBoxSports;
    @BindView(R.id.notification_travel)
    CheckBox notificatonCheckBoxTravel;
    public static final String SHARE_PREFERENCES_QUERY_NOTIFICATION = "SHARE_PREFERENCES_QUERY_NOTIFICATION";
    public static String query;
    public static String filterQuery;
    private DisposableObserver<SearchApi> disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        mySwitch = findViewById(R.id.switch_notification);


        this.configureToolbar();
        this.setSwitchNotification();
        this.configureCheckBox();
        requiredFields();
        saveNotificationQuery();

    }

    //------------------------
    //       TOOLBAR
    //------------------------
    private void configureToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    //------------------------
    // SWITCH NOTIFICATION
    //------------------------
    private void setSwitchNotification() {

        final SharedPreferences sharedPreferences = getSharedPreferences("isChecked", 0);
        boolean value = false;
        value = sharedPreferences.getBoolean("isChecked", value);
        mySwitch.setChecked(value);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    requiredFields();
                    setCalendarTime();
                    //query = notificationQuery.getText().toString();

                    // sharedPreferences.edit().putBoolean("isChecked", true).apply();
                    if (query != null && filterQuery != null)
                        Toast.makeText(getApplicationContext(), "You will receive one notification by day with " + query + " as filter", Toast.LENGTH_LONG).show();

                    // sharedPreferences.edit().putBoolean("isChecked", true).apply();
                } else {


                    // sharedPreferences.edit().putBoolean("isChecked", false).apply();
                    Toast.makeText(getApplicationContext(), "Notification disable", Toast.LENGTH_SHORT).show();
                    cancelAlarm();
                    Log.e("alarm", "alarm cancelled");

                }
            }
        });

    }

    //---------------------------
    //      ALARM MANAGER
    // --------------------------


    public void setCalendarTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 30);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        startAlarm(calendar);

        Log.e("alarm", "alarm set" + calendar.getTimeInMillis());
    }

    private void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        Log.e("alarm", "start alarm");
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, 0);

        alarmManager.cancel(pendingIntent);
    }


    //-------------------------------------
    //    CHECKBOX AND REQUIRED FIELDS
    //-------------------------------------

    public void configureCheckBox() {

        notificatonCheckBoxArts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "arts";
                notificatonCheckBoxArts.setChecked(true);
            }
        });
        notificatonCheckBoxBusinesss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "business";
                notificatonCheckBoxBusinesss.setChecked(true);
            }
        });
        notificatonCheckEntrepreneurss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "entrepreneurs";
                notificatonCheckEntrepreneurss.setChecked(true);
            }
        });
        notificatonCheckBoxPolitics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "politics";
                notificatonCheckBoxPolitics.setChecked(true);
            }
        });
        notificatonCheckBoxSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "sports";
                notificatonCheckBoxSports.setChecked(true);
            }
        });
        notificatonCheckBoxTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "travel";
                notificatonCheckBoxTravel.setChecked(true);
            }
        });

    }


    private void saveNotificationQuery() {

        notificationQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    //notificationQuery.setSingleLine(true);
                    query = notificationQuery.getText().toString();
                    Log.e("query", query);

                    return true;
                } else {
                    return false;
                }
            }
        });

        Log.e("notification", notificationQuery.toString());
    }

    // Show a toast if query or filterQuery are null and disable switch
    private void requiredFields() {
        if (query == null) {
            mySwitch.setChecked(false);
            Toast.makeText(this, "you must enter query term", Toast.LENGTH_SHORT).show();
        } else if (filterQuery == null) {
            mySwitch.setChecked(false);
            Toast.makeText(this, "Check at least one box ", Toast.LENGTH_SHORT).show();
        }
    }



}
