package com.example.doubl.mynews.MyNews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostPopular {

    @SerializedName("results")
    @Expose
    private List<ResultMostPopular> results = null;


    public List<ResultMostPopular> getResults() {
        return results;
    }
}
