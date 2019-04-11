package com.example.doubl.mynews.MyNews.Views.RecyclerViews;

import com.example.doubl.mynews.MyNews.Models.ResultMostPopular;
import com.example.doubl.mynews.MyNews.Models.ResultSearchApi;
import com.example.doubl.mynews.MyNews.Models.ResultTopStories;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateAllFragmentItemTest {

    // given
    private ResultMostPopular resultMostPopular = mock(ResultMostPopular.class);
    private ResultSearchApi resultSearchApi = mock(ResultSearchApi.class);
    private ResultTopStories resultTopStories = mock(ResultTopStories.class);
    private String section=  "my section";
    private String bodyText = "my body text";
    private String date = "2019-12-25";
    private  String expected;
    private String output;
    // when - then
    private void mockResultMostPopular(){
        when(resultMostPopular.getSection()).thenReturn(section);
        when(resultMostPopular.getTitle()).thenReturn(bodyText);
        when(resultMostPopular.getPublishedDate()).thenReturn(date);
    }
    private void mockResultSearchApi(){
        when(resultSearchApi.getSectionName()).thenReturn(section);
       // when(resultSearchApi.getHeadline().getMain()).thenReturn(bodyText);
        when(resultSearchApi.getPubDate()).thenReturn(date);
    }
    private void mockResultTopStories(){
         when(resultTopStories.getSection()).thenReturn(section);
        when(resultTopStories.getTitle()).thenReturn(bodyText);
        when(resultTopStories.getPublishedDate()).thenReturn(date);
    }
    @Test
    public void setSection() {
        expected= "my section";
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        this.mockResultMostPopular();
        output = updateAllFragmentItem.setSection(resultMostPopular);
        assertEquals(expected, output);
    }

    @Test
    public void setBodyText() {
        expected=  "my body text";
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        this.mockResultMostPopular();
        output = updateAllFragmentItem.setBodyText(resultMostPopular);
        assertEquals(expected, output);
    }

    @Test
    public void setParseDate() {
        expected = "25-12-2019";
        this.mockResultMostPopular();
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        output = updateAllFragmentItem.setParseDate(resultMostPopular);
        assertEquals(expected, output);
    }

    @Test
    public void setSectionSearchApi() {
        expected = "my section";
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        this.mockResultSearchApi();
        output = updateAllFragmentItem.setSection(resultSearchApi);
        assertEquals(expected, output);

    }


//   @Test
//   public void setSectionTopStories() {
//       expected = "my section";
//       UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
//       this.mockResultTopStories();
//     if (!resultTopStories.getSubsection().equals("")){
//         String myString = ">"+resultTopStories.getSubsection();
//         output = resultTopStories.getSection()+myString;
//     }else
//      // output = updateAllFragmentItem.setSection(resultTopStories);
//       output = updateAllFragmentItem.setSection(resultTopStories);
//       assertEquals(expected, output);
//   }

//    @Test
//    public void setBodyTextSearchApi() {
//        expected=  "my body text";
//        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
//        this.mockResultSearchApi();
//        output = updateAllFragmentItem.setBodyText(resultSearchApi);
//        assertEquals(expected, output);
//    }

    @Test
    public void setBodyTextTopStories() {
        expected = "my body text";
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        this.mockResultTopStories();
        output = updateAllFragmentItem.setBodyText(resultTopStories);
        assertEquals(expected, output);
    }


    @Test
    public void setParseDateTopStories() {
        expected = "25-12-2019";
        this.mockResultTopStories();
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        output = updateAllFragmentItem.setParseDateTopStories(resultTopStories);
        assertEquals(expected, output);
    }

    @Test
    public void setParseDateSearchArticle() {
        expected = "25-12-2019";
        this.mockResultSearchApi();
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        output = updateAllFragmentItem.setParseDateSearchArticle(resultSearchApi);
        assertEquals(expected, output);
    }
}