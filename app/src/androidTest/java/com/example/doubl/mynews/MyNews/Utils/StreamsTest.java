package com.example.doubl.mynews.MyNews.Utils;


import com.example.doubl.mynews.MyNews.Models.MostPopular;

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

}
