package com.example.doubl.mynews.Controller.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchApi {
    @SerializedName("response")
    @Expose
    private Response response;

    public Response getResponse() {
        return response;
    }

    public class Response {

        @SerializedName("docs")
        @Expose
        private List<ResultSearchApi> docs = null;

        public List<ResultSearchApi> getDocs() {
            return docs;
        }
    }
}
