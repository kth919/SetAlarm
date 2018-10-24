package com.noname.setalarm.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.noname.setalarm.MainActivity;
import com.noname.setalarm.R;

import java.util.UUID;

public class AlarmReceiver extends BroadcastReceiver {

    private static String TAG = AlarmReceiver.class.getName();
    private String id;

    @Override
    public void onReceive(Context context, Intent intent) {

        id = UUID.randomUUID().toString();

        NotificationManager notificationManager = null;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "test";
            String description = "알람입니다";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = context.getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }else {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

            Intent tmpIntent = new Intent(context, MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(tmpIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context, id)
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                            .setContentTitle("내 알림")
                            .setContentText("Hello World!")
                            .setContentIntent(resultPendingIntent);

            notificationManager.notify(1, mBuilder.build());

    }
}