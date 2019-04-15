package com.example.doubl.mynews.MyNews.Utils;


import com.example.doubl.mynews.MyNews.Models.MostPopular;
import com.example.doubl.mynews.MyNews.Models.SearchApi;
import com.example.doubl.mynews.MyNews.Models.TopStories;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;


@RunWith(JUnit4.class)
public class StreamsTest {

    @Test
    public void mostPopularArticle() {
        Observable<MostPopular> observableMostPopular = NewYorkTimesStream.streamFetchMostPopular(1);

        TestObserver<MostPopular> mostPopularTestObserver = new TestObserver<>();

        observableMostPopular.subscribeWith(mostPopularTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        MostPopular mostPopular = mostPopularTestObserver.values().get(0);
        Assert.assertTrue(mostPopular.getResults().size() > 0);

    }
 @Test
    public void topStories() {
        Observable<TopStories> observableTopStories = NewYorkTimesStream.streamFetchTopStories("sports");

        TestObserver<TopStories> topStoriesTestObserver = new TestObserver<>();

        observableTopStories.subscribeWith(topStoriesTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        TopStories topStories = topStoriesTestObserver.values().get(0);
        Assert.assertTrue(topStories.getResults().size() > 0);

    }
@Test
    public void searchArticle() {
        Observable<SearchApi> observableSearchApi = NewYorkTimesStream.streamFetchSearchArticle(null, "20190415",0,"arts",null, "newest", ApiKey.API_KEY );

        TestObserver<SearchApi> searchArticleTestObserver = new TestObserver<>();

        observableSearchApi.subscribeWith(searchArticleTestObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        SearchApi searchApi = searchArticleTestObserver.values().get(0);
        Assert.assertTrue(searchApi.getResponse().getDocs().size() > 0);

    }

}
