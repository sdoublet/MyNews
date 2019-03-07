package com.example.doubl.mynews.Controller.Views.RecyclerViews;

import com.example.doubl.mynews.Controller.Models.ResultMostPopular;

public class UpdateAllFragmentItem {

    private String string;

    public UpdateAllFragmentItem() {
    }

    public String setSection(ResultMostPopular article) {
        string = article.getSection();
        return string;
    }

    public String setBodyText(ResultMostPopular article) {
        if (!article.getTitle().equals("")) {
            string = article.getTitle();
        }
        return string;
    }

    public String setPublisheddate (ResultMostPopular article){
        string= article.getPublishedDate();
        return string;
    }
}
