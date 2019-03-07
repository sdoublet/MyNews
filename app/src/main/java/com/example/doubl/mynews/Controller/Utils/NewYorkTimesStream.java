package com.example.doubl.mynews.Controller.Utils;

import com.example.doubl.mynews.Controller.Models.MostPopular;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewYorkTimesStream {
    public static Observable<MostPopular> streamFetchMostPopular (int period){
        NewYorkTimesServices newYorkTimesServices = NewYorkTimesServices.retrofitMostPopular.create(NewYorkTimesServices.class);
        return newYorkTimesServices.getNYTMostPopular(period)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(20,TimeUnit.SECONDS);
    }
}
