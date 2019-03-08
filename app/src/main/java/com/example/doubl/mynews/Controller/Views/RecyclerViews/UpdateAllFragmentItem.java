package com.example.doubl.mynews.Controller.Views.RecyclerViews;

import com.example.doubl.mynews.Controller.Models.ResultMostPopular;
import com.example.doubl.mynews.Controller.Models.ResultTopStories;

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

    public String setBodyText(ResultMostPopular article) {
        if (!article.getTitle().equals("")) {
            string = article.getTitle();
        }
        return string;

    }public String setBodyText(ResultTopStories articleTopStories) {
        if (!articleTopStories.getTitle().equals("")) {
            string = articleTopStories.getTitle();
        }
        return string;
    }

    public String setPublisheddate (ResultMostPopular article){
        string= article.getPublishedDate();
        return string;
    }
    public String setPublisheddate (ResultTopStories articletopStories){
        string= articletopStories.getPublishedDate();
        return string;
    }
}
