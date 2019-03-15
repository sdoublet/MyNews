package com.example.doubl.mynews.Controller.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doubl.mynews.R;

import java.util.Calendar;


import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchToolbarActivity extends AppCompatActivity {

    @BindView(R.id.search_articles_button)
    Button searchArticleButton;
    @BindView(R.id.begin_date)
    TextView beginDatetext;
    @BindView(R.id.end_date)
    EditText endDateText;
    Calendar calendar;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_toolbar);
        ButterKnife.bind(this);
        this.configureToolbar();
        onButtonSearchArticleClicked();
        onBeginDateClicked();
        onEndDateClicked();
    }


    private void configureToolbar() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    private void onButtonSearchArticleClicked() {
        searchArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    //----------------------------
    // Open calendar and set text
    //-----------------------------
    private void onBeginDateClicked() {
        beginDatetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureDatePickerBeginDate();
            }
        });
    }

    private void onEndDateClicked() {
        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                configureDatePickerEndDate();
            }
        });
    }

    private void configureDatePickerBeginDate() {
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                beginDatetext.setText(date);

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

            }
        }, year, month, day);
        datePickerDialog.show();
    }

}


