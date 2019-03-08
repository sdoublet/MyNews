package com.example.doubl.mynews.Controller.Utils;

import com.example.doubl.mynews.Controller.Models.MostPopular;
import com.example.doubl.mynews.Controller.Models.TopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NewYorkTimesServices {
    @GET("{period}.json?api-key="+ApiKey.API_KEY)
    Observable<MostPopular>getNYTMostPopular(@Path("period")int period);

    Retrofit retrofitMostPopular = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/viewed/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("{section}.json?api-key="+ApiKey.API_KEY)
    Observable<TopStories>getNYTTopStories(@Path("section")String section);

    Retrofit retrofitTopStories = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

}
