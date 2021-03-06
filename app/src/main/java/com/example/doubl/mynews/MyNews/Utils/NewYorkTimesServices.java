package com.example.doubl.mynews.MyNews.Utils;

import com.example.doubl.mynews.MyNews.Models.MostPopular;
import com.example.doubl.mynews.MyNews.Models.SearchApi;
import com.example.doubl.mynews.MyNews.Models.TopStories;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewYorkTimesServices {
    @GET("{period}.json?api-key=" + ApiKey.API_KEY)
    Observable<MostPopular> getNYTMostPopular(@Path("period") int period);

    Retrofit retrofitMostPopular = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/mostpopular/v2/viewed/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("{section}.json?api-key=" + ApiKey.API_KEY)
    Observable<TopStories> getNYTTopStories(@Path("section") String section);

    Retrofit retrofitTopStories = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("articlesearch.json")
    Observable<SearchApi> getNYTSearchArticles(@Query("begin_date") String beginDate,
                                               @Query("end_date") String endDate,
                                               @Query("fq") String filter,
                                               @Query("q") String query,
                                               @Query("page") int page,
                                               @Query("sort") String sortOrder,
                                               @Query("api-key") String apiKey);


    Retrofit retrofitSearchArticles = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/search/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();


}
