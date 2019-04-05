package com.example.doubl.mynews;

import com.example.doubl.mynews.MyNews.Models.ResultMostPopular;
import com.example.doubl.mynews.MyNews.Views.RecyclerViews.UpdateAllFragmentItem;

import org.junit.Test;



import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ConvertToJson {

    // given
    private ResultMostPopular resultMostPopular = mock(ResultMostPopular.class);
    private String section=  "my section";
    private String bodyText = "my body text";
    private String date = "2019-12-25";
    private  String expected;
    private String output;

    // when - then
    private void mockResult(){
        when(resultMostPopular.getSection()).thenReturn(section);
        when(resultMostPopular.getTitle()).thenReturn(bodyText);
        when(resultMostPopular.getPublishedDate()).thenReturn(date);
    }
@Test
    public void verifySection(){
        expected= "my section";
    UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
    this.mockResult();
    output = updateAllFragmentItem.setSection(resultMostPopular);
    assertEquals(expected, output);
}

@Test
    public void verifyBodyText(){
        expected=  "my body text";
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        this.mockResult();
    output = updateAllFragmentItem.setBodyText(resultMostPopular);
    assertEquals(expected, output);
    }

    @Test
    public void setParseDate(){
        expected = "25-12-2019";
        this.mockResult();
        UpdateAllFragmentItem updateAllFragmentItem = new UpdateAllFragmentItem();
        output = updateAllFragmentItem.setParseDate(resultMostPopular);
        assertEquals(expected, output);
    }
    //can do the same for search and topStories
}

