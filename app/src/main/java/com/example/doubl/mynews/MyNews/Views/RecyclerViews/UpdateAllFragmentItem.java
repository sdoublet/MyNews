package com.example.doubl.mynews.MyNews.Views.RecyclerViews;

import android.annotation.SuppressLint;

import com.example.doubl.mynews.MyNews.Models.ResultMostPopular;
import com.example.doubl.mynews.MyNews.Models.ResultSearchApi;
import com.example.doubl.mynews.MyNews.Models.ResultTopStories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class UpdateAllFragmentItem {

    private String string;

    UpdateAllFragmentItem() {
    }


    String setSection(ResultMostPopular article) {
        string = article.getSection();

        return string;
    }

    String setSection(ResultTopStories articleTopStories) {

        if (!articleTopStories.getSubsection().equals("")) {
            String myString = " > " + articleTopStories.getSubsection();
            string = articleTopStories.getSection() + myString;
        } else string = articleTopStories.getSection();
        return string;
    }


    String setSection(ResultSearchApi articleSearchApi) {
        string = articleSearchApi.getSectionName();

        return string;
    }

    String setBodyText(ResultMostPopular article) {

        string = article.getTitle();

        return string;

    }

    String setBodyText(ResultTopStories articleTopStories) {
        string = articleTopStories.getTitle();

        return string;
    }

    String setBodyText(ResultSearchApi artilcleSearchApi) {
        string = artilcleSearchApi.getHeadline().getMain();
        return string;
    }



     String setParseDate(ResultMostPopular resultMostPopular) {
        String string = "";
        try {
            String dateParse = resultMostPopular.getPublishedDate();
            @SuppressLint("SimpleDateFormat") DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = date.parse(dateParse);
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateParse = dateFormat.format(date1);
            string = dateParse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return string;
    }

    String setParseDateTopStories(ResultTopStories resultMostTopStories) {
        String string = "";
        try {
            String dateParse = resultMostTopStories.getPublishedDate();
            @SuppressLint("SimpleDateFormat") DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = date.parse(dateParse);
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateParse = dateFormat.format(date1);
            string = dateParse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return string;
    }

    String setParseDateSearchArticle(ResultSearchApi resultSearchApi) {
        String string = "";
        try {
            String dateParse = resultSearchApi.getPubDate();
            @SuppressLint("SimpleDateFormat") DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = date.parse(dateParse);
            @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateParse = dateFormat.format(date1);
            string = dateParse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return string;
    }

}
