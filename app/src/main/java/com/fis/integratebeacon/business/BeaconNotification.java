package com.fis.integratebeacon.business;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
import androidx.core.app.NotificationManagerCompat;

import com.fis.integratebeacon.R;
import com.fis.integratebeacon.configs.Constant;
import com.fis.integratebeacon.entity.BeaconsNotificationEntity;
import com.fis.integratebeacon.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Handler;

public class BeaconNotification {

    private Context context;

    BeaconNotification(Context context) {
        this.context = context;
    }

    public Builder create(BeaconsNotificationEntity beaconsNotificationEntity) {
        Builder mBuilder = null;

        try {
            mBuilder = new Builder(context, Constant.NOTIFICATION.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setContentTitle(beaconsNotificationEntity.getName())
                    .setContentText(beaconsNotificationEntity.getContent())
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(beaconsNotificationEntity.getContent()))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                    .setVibrate(new long[]{0, 500, 1000})
                    .setDefaults(NotificationCompat.DEFAULT_LIGHTS)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

                    .setGroup(Constant.NOTIFICATION.GROUP_KEY)
                    .setWhen(System.currentTimeMillis());
            ;


            if (StringUtils.isUrl(beaconsNotificationEntity.getLink())) {
                Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
                notificationIntent.setData(Uri.parse(beaconsNotificationEntity.getLink()));
                PendingIntent pi = PendingIntent.getActivity(context, 0, notificationIntent, 0);

                mBuilder
                        .setTicker("yortext")
                        .setContentIntent(pi);
            }

            Bitmap b = urlToBitmap(Constant.API_URL + beaconsNotificationEntity.getImagePath());

            if (b != null) {
                mBuilder.setLargeIcon(b);
            }

        } catch (Exception e) {
            Log.e(Constant.LOG.NOTIFICATION, e.getMessage() == null ? "" : e.getMessage());
        }

        return mBuilder;
    }

    public List<Builder> create(List<BeaconsNotificationEntity> lstNoti) {
        List<Builder> output = new ArrayList<>();

        for (BeaconsNotificationEntity item : lstNoti) {
            Builder b = this.create(item);
            if (b != null) output.add(b);
        }

        return output;
    }

    public static Integer showNotification(Builder builder, Context context) {

        try {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            // notificationId is a unique int for each notification that you must define
            int notificationId = (int) new Random().nextInt((1000 - 2 + 1) + 2);
            notificationManager.notify(notificationId, builder.build());
            return notificationId;
        } catch (Exception e) {
            Log.e(Constant.LOG.NOTIFICATION, e.getMessage() == null ? "" : e.getMessage());
        }

        return null;
    }

    /* need add to asyncTask */
    private Bitmap urlToBitmap(String stringUrl) {

        try {
            URL url = new URL(stringUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream in = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(in);
            return myBitmap;
        } catch (IOException e) {
            Log.e(Constant.LOG.URL_TO_BITMAP, e.getMessage() == null ? "" : e.getMessage());
            return null;
        }
    }
}
