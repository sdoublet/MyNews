package com.example.doubl.mynews.MyNews.Activities;

import android.widget.CheckBox;
import android.widget.Switch;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import retrofit2.Invocation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

public class NotificationActivityTest  {


    private String query = "my query";
    private String toast = "my toast";
    private String expected;
    private String output;

    @Test
    public void requiredFields() {
        expected = "my toast";
        query = null;
        if (query == null) {
            output = toast;
        }
        assertEquals(expected, output);

    }


}
