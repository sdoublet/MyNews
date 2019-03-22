package com.example.doubl.mynews.MyNews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopStories {

    @SerializedName("results")
    @Expose
    private List<ResultTopStories> results = null;


    public List<ResultTopStories> getResults() {
        return results;
    }
}
