package com.example.doubl.mynews.MyNews.Utils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.doubl.mynews.MyNews.Activities.NotificationActivity;

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationActivity notificationActivity = new NotificationActivity();
        notificationActivity.sendNotification(context);
    }
}
