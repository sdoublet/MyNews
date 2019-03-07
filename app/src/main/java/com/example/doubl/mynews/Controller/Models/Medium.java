package com.example.doubl.mynews.Controller.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class Medium {
    @SerializedName("media-metadata")
    @Expose
    private List<MediaMetadatum> mediaMetadata = null;


    public List<MediaMetadatum> getMediaMetadata() {
        return mediaMetadata;
    }
}
