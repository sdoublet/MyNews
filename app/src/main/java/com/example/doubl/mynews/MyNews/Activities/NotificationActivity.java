package com.example.doubl.mynews.MyNews.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doubl.mynews.MyNews.Utils.AlertReceiver;
import com.example.doubl.mynews.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NotificationActivity extends AppCompatActivity {


    Switch mySwitch = null;
    @BindView(R.id.query_text)
    EditText notificationQuery;
    @BindView(R.id.checkbox_arts)
    CheckBox notificationCheckBoxArts;
    @BindView(R.id.checkbox_business)
    CheckBox notificationCheckBoxBusiness;
    @BindView(R.id.checkbox_entrepreneurs)
    CheckBox notificationCheckEntrepreneurs;
    @BindView(R.id.checkbox_politics)
    CheckBox notificationCheckBoxPolitics;
    @BindView(R.id.checkbox_sports)
    CheckBox notificationCheckBoxSports;
    @BindView(R.id.checkbox_travel)
    CheckBox notificationCheckBoxTravel;
    @BindView(R.id.search_articles_button)
    Button searchButton;
    @BindView(R.id.begin_date)
    TextView beginDateText;
    @BindView(R.id.end_date)
    EditText endDateText;
    @BindView(R.id.checkbox_sum)
    LinearLayout checkBoxSum;
    @BindView(R.id.since_textView)
    TextView sinceTextView;
    @BindView(R.id.to_textView)
    TextView toTextView;
    public static String query;
    public static String filterQuery;
    public static List<String> filterListChecked = new ArrayList<>();

    // TODO: 02/04/2019 resolve this
    public static String resultFilterQuery;

    public static String getResultFilterQuery() {
        return resultFilterQuery;
    }


    public static void setResultFilterQuery(String resultFilterQuery) {
        NotificationActivity.resultFilterQuery = resultFilterQuery;
    }
// SharePreferences pour alertReceiver


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_toolbar);
        ButterKnife.bind(this);
        mySwitch = findViewById(R.id.switch_notification);
        this.setVisibility();
        this.configureToolbar();
        this.setSwitchNotification();
        this.configureCheckBox();
        this.requiredFields();
        this.saveNotificationQuery();

    }


    //------------------------
    //      FOR DESIGN
    //------------------------

    //ToolBar
    private void configureToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    // Set visibility to hide elements we don't need in the layout
    private void setVisibility() {
        toTextView.setVisibility(View.GONE);
        sinceTextView.setVisibility(View.GONE);
        endDateText.setVisibility(View.GONE);
        beginDateText.setVisibility(View.GONE);
        searchButton.setVisibility(View.GONE);
    }

    //------------------------
    // SWITCH NOTIFICATION
    //------------------------
    private void setSwitchNotification() {

        final SharedPreferences sharedPreferences = getSharedPreferences("isChecked", 0);
        boolean value ;
        value = sharedPreferences.getBoolean("isChecked", false);
        mySwitch.setChecked(value);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    requiredFields();
                    setCalendarTime();


                    if (query != null && filterQuery != null)
                        Toast.makeText(getApplicationContext(), "You will receive one notification by day with " + query + " as filter", Toast.LENGTH_LONG).show();
                } else {
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
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 38);
        calendar.set(Calendar.SECOND, 30);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        startAlarm(calendar);

        Log.e("alarm", "alarm set" + calendar.getTimeInMillis());
    }

    public void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("query", query);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        Log.e("alarm", "start alarm");
        // TODO: 02/04/2019 resolve this
        // resultFilterQuery = TextUtils.join(" ", filterListChecked);
        setResultFilterQuery(TextUtils.join(" ", filterListChecked));
        Log.e("filter", "fq: " + getResultFilterQuery());


        Log.e("query", "query " + query);
    }

    public void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, 0);

        alarmManager.cancel(pendingIntent);
    }


    //-------------------------------------
    //    CHECKBOX AND REQUIRED FIELDS
    //-------------------------------------


    public void configureCheckBox() {

        filterListChecked.clear();
        notificationCheckBoxArts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filterQuery = "arts";
                    filterListChecked.add(filterQuery);
                    notificationCheckBoxArts.setChecked(true);
                } else {
                    filterListChecked.remove(filterQuery);
                    notificationQuery = null;
                    notificationCheckBoxArts.setChecked(false);
                }
            }
        });
        notificationCheckBoxBusiness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filterQuery = "business";
                    filterListChecked.add(filterQuery);
                    notificationCheckBoxBusiness.setChecked(true);
                } else {
                    filterListChecked.remove(filterQuery);
                    notificationQuery = null;
                    notificationCheckBoxBusiness.setChecked(false);
                }
            }
        });
        notificationCheckEntrepreneurs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filterQuery = "entrepreneurs";
                    filterListChecked.add(filterQuery);
                    notificationCheckEntrepreneurs.setChecked(true);
                } else {
                    filterListChecked.remove(filterQuery);
                    notificationQuery = null;
                    notificationCheckEntrepreneurs.setChecked(false);
                }
            }
        });
        notificationCheckBoxPolitics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filterQuery = "politics";
                    filterListChecked.add(filterQuery);
                    notificationCheckBoxPolitics.setChecked(true);
                } else {
                    filterListChecked.remove(filterQuery);
                    filterQuery = null;
                    notificationCheckBoxPolitics.setChecked(false);
                }
            }
        });
        notificationCheckBoxSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filterQuery = "sports";
                    filterListChecked.add(filterQuery);
                    notificationCheckBoxSports.setChecked(true);
                } else {
                    filterListChecked.remove(filterQuery);
                    filterQuery = null;
                    notificationCheckBoxSports.setChecked(false);
                }
            }
        });
        notificationCheckBoxTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    filterQuery = "travel";
                    filterListChecked.add(filterQuery);
                    notificationCheckBoxTravel.setChecked(true);
                } else {
                    filterListChecked.remove(filterQuery);
                    filterQuery = null;
                    notificationCheckBoxTravel.setChecked(false);
                }
            }
        });

    }


    public void saveNotificationQuery() {

        notificationQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    // save query text
                    query = notificationQuery.getText().toString();
                    Log.e("query ", query);

                    // to hide keyboard when press enter
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                } else {
                    return false;
                }
            }
        });

        Log.e("notification", notificationQuery.toString());
    }

    // Show a toast if query or filterQuery are null and disable switch
    public void requiredFields() {
        if (query == null) {
            mySwitch.setChecked(false);
            Toast.makeText(this, "you must enter query term", Toast.LENGTH_SHORT).show();
        } else if (filterQuery == null) {
            mySwitch.setChecked(false);
            Toast.makeText(this, "Check at least one box ", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: 01/04/2019 change this
    @Override
    protected void onPostResume() {
        super.onPostResume();
        filterQuery = null;
        //query=null;
    }


}

