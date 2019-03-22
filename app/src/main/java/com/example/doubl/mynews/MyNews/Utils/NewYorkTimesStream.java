package com.example.doubl.mynews.MyNews.Utils;

import com.example.doubl.mynews.MyNews.Models.MostPopular;
import com.example.doubl.mynews.MyNews.Models.SearchApi;
import com.example.doubl.mynews.MyNews.Models.TopStories;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewYorkTimesStream {

    public static Observable<MostPopular> streamFetchMostPopular(int period) {
        NewYorkTimesServices newYorkTimesServices = NewYorkTimesServices.retrofitMostPopular.create(NewYorkTimesServices.class);
        return newYorkTimesServices.getNYTMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20, TimeUnit.SECONDS);
    }

    public static Observable<TopStories> streamFetchTopStories(String section) {
        NewYorkTimesServices newYorkTimesServices = NewYorkTimesServices.retrofitTopStories.create(NewYorkTimesServices.class);
        return newYorkTimesServices.getNYTTopStories(section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20, TimeUnit.SECONDS);
    }



    public static Observable<SearchApi> streamFetchSearchArticle(String beginDate, String endDate,
                                                                  int page,String filter,String query,   String sortOrder, String apiKey) {
        NewYorkTimesServices newYorkTimesServices = NewYorkTimesServices.retrofitSearchArticles.create(NewYorkTimesServices.class);
        return newYorkTimesServices.getNYTSearchArticles(beginDate, endDate,filter,query,page, sortOrder, apiKey )
                .subscribeOn(Schedulers.io())//thread dédié
                .observeOn(AndroidSchedulers.mainThread())//tous les subscriber doivent écouter
                .timeout(20, TimeUnit.SECONDS);
    }
}
