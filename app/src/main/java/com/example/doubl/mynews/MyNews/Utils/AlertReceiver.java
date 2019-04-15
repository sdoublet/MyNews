package com.example.doubl.mynews.MyNews.Utils;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.doubl.mynews.MyNews.Activities.MainActivity;
import com.example.doubl.mynews.MyNews.Activities.NotificationActivity;
import com.example.doubl.mynews.MyNews.Models.SearchApi;
import com.example.doubl.mynews.R;

import java.util.Calendar;

import io.reactivex.observers.DisposableObserver;

public class AlertReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private int articleFound;
    private Context context;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        executeHttpRequestWithRetrofit();
        this.context = context;

        Log.e("notification", "notification send");
    }


    //---------------------------
    //  CALL API / HTTP REQUEST
    //---------------------------
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void executeHttpRequestWithRetrofit() {

        Calendar c = Calendar.getInstance();
        Calendar calBeginDate = Calendar.getInstance();
        calBeginDate.add(Calendar.DATE, -100);
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String mEndDate = sdf.format(c.getTime());
        final String mBeginDate = sdf.format(calBeginDate.getTime());


        DisposableObserver<SearchApi> disposable = NewYorkTimesStream.streamFetchSearchArticle(mBeginDate, mEndDate, 0, NotificationActivity.getResultFilterQuery(), NotificationActivity.query, "newest", ApiKey.API_KEY).subscribeWith(new DisposableObserver<SearchApi>() {
            @Override
            public void onNext(SearchApi searchApi) {

                articleFound = searchApi.getResponse().getDocs().size();


                Log.e("TAG", searchApi.getStatus());
                Log.e("TAG", searchApi.getResponse().toString());
                Log.e("TAG", "SearchApi size: " + Integer.toString(articleFound));
                Log.e("TAG", "endDate " + mEndDate);
                Log.e("TAG", "beginDate: " + mBeginDate);
                Log.e("TAG", "queryInput: " + NotificationActivity.query);
                Log.e("TAG", "filter " + NotificationActivity.getResultFilterQuery());

                sendNotification(context);

            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "On Error..." + Log.getStackTraceString(e));

            }

            @Override
            public void onComplete() {
                Log.e("TAG", "On complete!");
            }
        });
    }

    //--------------------------------
    //  CONFIGURE NOTIFICATION
    //--------------------------------
    public void sendNotification(Context context) {


        //set notification's tap action
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //set notification content
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationBuilder.setContentTitle("MyNews")
                .setContentText("you have " + articleFound + " new article could interest you")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        createNotificationChannel(context);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, notificationBuilder.build());

    }

    public void createNotificationChannel(Context context) {
        //only for Oreo version and sup
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
