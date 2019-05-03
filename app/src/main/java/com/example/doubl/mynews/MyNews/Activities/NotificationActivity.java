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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    SharedPreferences mSharePreferences;
    String MY_PREF = "my_preference";

    // TODO: 02/04/2019 resolve this
    public static String resultFilterQuery;


    public static String getResultFilterQuery() {
        return resultFilterQuery;
    }


    public static void setResultFilterQuery(String resultFilterQuery) {
        NotificationActivity.resultFilterQuery = resultFilterQuery;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_toolbar);
        ButterKnife.bind(this);
        mySwitch = findViewById(R.id.switch_notification);
        mSharePreferences = getSharedPreferences(MY_PREF, 0);
        this.setVisibility();
        this.configureToolbar();
        this.setSwitchNotification();
        this.configureCheckBox();
        this.requiredFields();
        this.saveNotificationQueryWithKeyboard();
        this.saveQueryWithoutKeyboard();
        notificationQuery.setText(mSharePreferences.getString("queryPref", ""));
        mySwitch.setChecked(mSharePreferences.getBoolean("switchChecked", false));

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

    /**
     * Hide elements we don't need for this activity in the layout
     */
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

    /**
     * Change notification behavior depending on switch is checked or not
     */
    private void setSwitchNotification() {


        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    requiredFields();
                    setCalendarTime();
                    SharedPreferences.Editor editor = mSharePreferences.edit();
                    editor.putBoolean("switchChecked", true);
                    editor.apply();

                    if ((query != null && !query.equals("")) && filterListChecked.size() != 0) {
                        Toast.makeText(getApplicationContext(), "You will receive one notification by day with " + query + " as query", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Notification disable", Toast.LENGTH_SHORT).show();
                    cancelAlarm();
                    SharedPreferences.Editor editor = mSharePreferences.edit();
                    editor.putBoolean("switchChecked", false);
                    editor.apply();

                    Log.e("alarm", "alarm cancelled");
                }
            }
        });

    }

    //---------------------------
    //      ALARM MANAGER
    // --------------------------


    /**
     * Set the hour of alarm set
     */
    public void setCalendarTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 30);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        startAlarm(calendar);

        Log.e("alarm", "alarm set" + calendar.getTimeInMillis());
    }

    /**
     * Create pending intent and set type of repeating notification sent
     * Call AlertReceiver
     *
     * @param calendar this
     */
    public void startAlarm(Calendar calendar) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.putExtra("query", query);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.e("alarm", "start alarm");
        setResultFilterQuery(TextUtils.join(" ", filterListChecked));
        Log.e("filter", "fq: " + getResultFilterQuery());


        Log.e("query", "query " + query);
    }

    /**
     * Cancel notification sent by cancelling alarm
     */
    public void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, 0);

        alarmManager.cancel(pendingIntent);
    }


    //-------------------------------------
    //    CHECKBOX AND REQUIRED FIELDS
    //-------------------------------------

    /**
     * Set behavior of checkbox depending on checked status
     * Add or remove filterQuery in the filterListChecked
     */
    public void configureCheckBox() {
        filterListChecked.clear();
        setupCheckbox(notificationCheckBoxArts, "arts");
        notificationCheckBoxArts.setChecked(mSharePreferences.getBoolean("arts", false));
        setupCheckbox(notificationCheckBoxBusiness, "business");
        notificationCheckBoxBusiness.setChecked(mSharePreferences.getBoolean("business", false));
        setupCheckbox(notificationCheckEntrepreneurs, "entrepreneurs");
        notificationCheckEntrepreneurs.setChecked(mSharePreferences.getBoolean("entrepreneurs", false));
        setupCheckbox(notificationCheckBoxPolitics, "politics");
        notificationCheckBoxPolitics.setChecked(mSharePreferences.getBoolean("politics", false));
        setupCheckbox(notificationCheckBoxSports, "sports");
        notificationCheckBoxSports.setChecked(mSharePreferences.getBoolean("sports", false));
        setupCheckbox(notificationCheckBoxTravel, "travel");
        notificationCheckBoxTravel.setChecked(mSharePreferences.getBoolean("travel", false));

    }

    public void setupCheckbox(final CheckBox notificationCheckBox, final String category) {
        notificationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = mSharePreferences.edit();
                filterQuery = category;

                if (isChecked) {
                    filterListChecked.add(filterQuery);
                    editor.putBoolean(category, true);
                } else {
                    filterListChecked.remove(filterQuery);
                    editor.putBoolean(category, false);
                }
                editor.apply();
            }
        });

    }

    /**
     * Retrieve text in query's edit text when enter button is pressed on keyboard
     * Hide keyboard
     */
    public void saveNotificationQueryWithKeyboard() {

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

    /**
     * Save query in edit text even if user doesn't press on enter button on keyboard
     */
    public void saveQueryWithoutKeyboard() {
        notificationQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                query = notificationQuery.getText().toString();
                SharedPreferences.Editor editor = mSharePreferences.edit();
                editor.putString("queryPref", notificationQuery.getText().toString());
                editor.apply();
            }
        });
    }

    /**
     * Display a message when user doesn't write a query term or doesn't check a box
     * Switch can't be checked
     */
    // Show a toast if query or filterQuery are null and disable switch
    public void requiredFields() {
        if (query == null || query.equals("")) {
            mySwitch.setChecked(false);
            Toast.makeText(this, "you must enter query term", Toast.LENGTH_SHORT).show();
        } else if (filterListChecked.size() == 0) {
            mySwitch.setChecked(false);
            Toast.makeText(this, "Check at least one box ", Toast.LENGTH_SHORT).show();
        }
    }


}

