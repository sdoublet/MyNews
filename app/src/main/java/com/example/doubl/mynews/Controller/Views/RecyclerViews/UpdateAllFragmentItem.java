package com.example.doubl.mynews.Controller.Views.RecyclerViews;

import com.example.doubl.mynews.Controller.Models.ResultMostPopular;
import com.example.doubl.mynews.Controller.Models.ResultSearchApi;
import com.example.doubl.mynews.Controller.Models.ResultTopStories;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateAllFragmentItem {

    private String string;

    public UpdateAllFragmentItem() {
    }

    public String setSection(ResultMostPopular article) {
        string = article.getSection();
        return string;
    }

    public String setSection(ResultTopStories articleTopStories) {
        string = articleTopStories.getSection();
        return string;
    }

    public String setSection(ResultSearchApi articleSearchApi) {
        string = articleSearchApi.getSectionName();
        return string;
    }

    public String setBodyText(ResultMostPopular article) {
        if (!article.getTitle().equals("")) {
            string = article.getTitle();
        }
        return string;

    }

    public String setBodyText(ResultTopStories articleTopStories) {
        if (!articleTopStories.getTitle().equals("")) {
            string = articleTopStories.getTitle();
        }
        return string;
    }

    public String setBodyText(ResultSearchApi artilcleSearchApi) {
        if (!artilcleSearchApi.getHeadline().getPrintHeadline().equals("")) {
            string = artilcleSearchApi.getSectionName();
        }
        return string;
    }

    public String setPublisheddate(ResultMostPopular article) {
        string = article.getPublishedDate();
        return string;
    }

    public String setPublisheddate(ResultTopStories articletopStories) {
        string = articletopStories.getPublishedDate();
        return string;
    }

    public String setPublisheddate(ResultSearchApi articleSearchApi) {
        string = articleSearchApi.getPubDate();
        return string;
    }
    public String setParseDate(ResultMostPopular resultMostPopular){
        String string="";
        try {
            String dateParse = resultMostPopular.getPublishedDate();
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = date.parse(dateParse);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateParse = dateFormat.format(date1);
            string = dateParse;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return string;
    }  public String setParseDateTopStories(ResultTopStories resultMostTopStories){
        String string="";
        try {
            String dateParse = resultMostTopStories.getPublishedDate();
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = date.parse(dateParse);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            dateParse = dateFormat.format(date1);
            string = dateParse;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return string;
    }
}
