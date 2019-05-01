package com.example.doubl.mynews.MyNews.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchToolbarActivity extends AppCompatActivity {
    @BindView(R.id.query_text)
    EditText queryText;
    @BindView(R.id.search_articles_button)
    Button searchArticleButton;
    @BindView(R.id.begin_date)
    TextView beginDateText;
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
    private List<String> filterListChecked = new ArrayList<>();
    private String resultFilterQuery;

    public String getResultFilterQuery() {
        return resultFilterQuery;
    }

    public void setResultFilterQuery(String resultFilterQuery) {
        this.resultFilterQuery = resultFilterQuery;
    }

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

    /**
     * Save beginDate, endDate, query and resultFilterQuery when user press button
     * Put these values on ResultSearchActivity
     * Launch ResultSearchActivity to show the result of the search in a recyclerView
     * At least one filterQuery's checkbox must be checked to display button
     */
    public void onButtonSearchArticleClicked() {
        searchArticleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = queryText.getText().toString();
                setResultFilterQuery(TextUtils.join(" ", filterListChecked));
                Intent intent = new Intent(getApplicationContext(), ResultSearchActivity.class);
                intent.putExtra("beginDate", beginDate);
                intent.putExtra("endDate", endDate);
                intent.putExtra("query", query);
                intent.putExtra("resultFilterQuery", getResultFilterQuery());
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
        beginDateText.setOnClickListener(new View.OnClickListener() {
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

    /**
     * Set DatePicker for beginDate
     * beginDateText is for display the date on the screen
     * beginDate is in another format for the request
     */
    public void configureDatePickerBeginDate() {
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                beginDateText.setText(date);


// set beginDate for httpRequest

                beginDate = year + "" + (month < 10 ? ("0" + (month + 1)) : (month)) + "" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth));

            }
        }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * Set DatePicker for endDate
     * endDateText is for display the date on the screen
     * endDate is in another format for the request
     */
    public void configureDatePickerEndDate() {
        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);


        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                endDateText.setText(date);
                endDate = year + "" + (month < 10 ? ("0" + (month + 1)) : (month)) + "" + (dayOfMonth < 10 ? ("0" + dayOfMonth) : (dayOfMonth));

            }
        }, year, month, day);
        datePickerDialog.show();
        Log.e("date", "" + year + month + day);

    }

    /**
     * Set behavior depending on checkboxes are checked or not
     * Add or remove filterQuery in filterListChecked
     * Set visibility of searchArticleButton
     */
    public void configureCheckBox() {
        filterListChecked.clear();
        setupCheckbox(checkBoxArts, "arts");
        setupCheckbox(checkBoxBusiness, "business");
        setupCheckbox(checkBoxEntrepreneurs, "entrepreneurs");
        setupCheckbox(checkBoxPolitics, "politics");
        setupCheckbox(checkBoxSports, "sports");
        setupCheckbox(checkBoxTravel, "travel");

    }

    public void setupCheckbox(final CheckBox checkBox, final String category) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filterQuery = category;
                if (isChecked) {
                    filterListChecked.add(filterQuery);
                    searchArticleButton.setVisibility(View.VISIBLE);
                    checkBox.setChecked(true);


                } else {
                    checkBox.setChecked(false);
                    filterListChecked.remove(filterQuery);
                    if (filterListChecked.size() == 0) {
                        searchArticleButton.setVisibility(View.GONE);
                    }
                }
            }

        });
    }


}


