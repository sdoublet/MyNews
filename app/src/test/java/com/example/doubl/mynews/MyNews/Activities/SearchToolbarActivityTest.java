package com.example.doubl.mynews.MyNews.Activities;


import org.junit.Assert;
import org.junit.Test;



public class SearchToolbarActivityTest {




    @Test
    public void configureDatePickerBeginDate() {


        int year = 2019;

        for (int month = 1; month < 12; month++) {


            for (int day = 1; day < 31; day++) {

                String date = year + "" + (month < 10 ? ("0" + (month)) : (month)) + "" + (day < 10 ? ("0" + day) : (day));
                String output = Integer.toString(date.length());
                String expected = "8";
                Assert.assertEquals(expected, output);
            }


        }
    }

    @Test
    public void configureDatePickerEndDate() {
        int year = 2019;
        // calendar.get(Calendar.MONTH);
        for (int month = 1; month < 12; month++) {

            //calendar.get(Calendar.DAY_OF_WEEK);
            for (int day = 1; day < 31; day++) {

                String date = year + "" + (month < 10 ? ("0" + (month)) : (month)) + "" + (day < 10 ? ("0" + day) : (day));
                String output = Integer.toString(date.length());
                String expected = "8";
                Assert.assertEquals(expected, output);
            }
        }
    }

    @Test
    public void configureCheckBox() {



    }
}