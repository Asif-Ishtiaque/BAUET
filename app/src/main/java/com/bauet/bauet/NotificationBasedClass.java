package com.bauet.bauet;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationBasedClass extends Application {
public static final String CHANNEL_ID_1 ="FARDIN1";
public static final String CHANNEL_ID_2 ="FARDIN2";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }

    public void  createNotification()
   {
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
       {
           NotificationChannel channel1 = new NotificationChannel(
                   CHANNEL_ID_1,
                   "Channel 1",
                   NotificationManager.IMPORTANCE_HIGH

           );
           NotificationChannel channel2 = new NotificationChannel(
                   CHANNEL_ID_2,
                   "Channel 2",
                   NotificationManager.IMPORTANCE_DEFAULT

           );

           NotificationManager manager = getSystemService(NotificationManager.class);
           manager.createNotificationChannel(channel1);
           manager.createNotificationChannel(channel2);
       }
   }
}
