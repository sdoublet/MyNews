package com.example.doubl.mynews.MyNews.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.doubl.mynews.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchToolbarActivity extends AppCompatActivity {
    @BindView(R.id.query_text)
    EditText queryText;
    @BindView(R.id.search_articles_button)
    Button searchArticleButton;
    @BindView(R.id.begin_date)
    TextView beginDatetext;
    @BindView(R.id.end_date)
    EditText endDateText;
    @BindView(R.id.checkbox_arts)
    CheckBox checkBoxArts;
    @BindView(R.id.checkbox_entrepreneurs)
    CheckBox checkBoxEntrepreneurs;
    @BindView(R.id.checkbox_politics)
    CheckBox checkBoxPolitics;
    @BindView(R.id.checkbox_sports)
    CheckBox checkBoxSports;
    @BindView(R.id.checkbox_business)
    CheckBox checkBoxBusiness;
    @BindView(R.id.checkbox_travel)
    CheckBox checkBoxTravel;
    @BindView(R.id.switch_layout)
    LinearLayout switchLayout;
    Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private String beginDate;
    private String endDate;
    private String query;
    private String filterQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_toolbar);
        ButterKnife.bind(this);
        switchLayout.setVisibility(View.INVISIBLE);
        this.configureToolbar();
        this.onButtonSearchArticleClicked();
        this.onBeginDateClicked();
        this.onEndDateClicked();
        this.configureCheckBox();


    }


    private void configureToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    public void onButtonSearchArticleClicked() {
        searchArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = queryText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), ResultSearchActivity.class);
                intent.putExtra("beginDate", beginDate);
                intent.putExtra("endDate", endDate);
                intent.putExtra("query", query);
                intent.putExtra("filterQuery", filterQuery);
                startActivity(intent);


            }
        });
        if (filterQuery == null) {
            searchArticleButton.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "you must check a box", Toast.LENGTH_SHORT).show();
        }
    }

    //----------------------------
    // Open calendar and set text
    //-----------------------------
    public void onBeginDateClicked() {
        beginDatetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureDatePickerBeginDate();
            }
        });
    }

    public void onEndDateClicked() {
        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                configureDatePickerEndDate();

            }
        });
    }

    public void configureDatePickerBeginDate() {
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                beginDatetext.setText(date);


// set beginDate for httpRequest
                //ResultSearchActivity.beginDate = year + "" + (month < 10 ? ("0" + (month + 1)) : (month + 1)) + "" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth));
                beginDate = year + "" + (month < 10 ? ("0" + (month + 1)) : (month )) + "" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth));

            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void configureDatePickerEndDate() {
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                endDateText.setText(date);
                endDate = year + "" + (month < 10 ? ("0" + (month + 1)) : (month )) + "" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth));

            }
        }, year, month, day);
        datePickerDialog.show();

    }

    public void configureCheckBox() {

        checkBoxArts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "arts";
                searchArticleButton.setVisibility(View.VISIBLE);

            }
        });
        checkBoxBusiness.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "business";
                searchArticleButton.setVisibility(View.VISIBLE);

            }
        });
        checkBoxEntrepreneurs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "entrepreneurs";
                searchArticleButton.setVisibility(View.VISIBLE);

            }
        });
        checkBoxPolitics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "politics";
                searchArticleButton.setVisibility(View.VISIBLE);

            }
        });
        checkBoxSports.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               filterQuery = "sports";
                searchArticleButton.setVisibility(View.VISIBLE);

            }
        });
        checkBoxTravel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = "travel";
                searchArticleButton.setVisibility(View.VISIBLE);

            }
        });

    }




}


