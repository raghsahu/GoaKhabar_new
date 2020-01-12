package dev.news.goakhabarr.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import dev.news.goakhabarr.Activity.MainActivity;
import dev.news.goakhabarr.R;
import dev.news.goakhabarr.Session.SessionManager;
import dev.news.goakhabarr.Utils.Constants;

/**
 * Created by Raghvendra Sahu on 25-Dec-19.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private String title_msg = "";
    RemoteMessage remoteMessage;
    private String user_id = "";


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        SessionManager session = new SessionManager(this);
        session.saveToken(s);

        Log.e("token_fcm_service", s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        //Log.e(TAG, "Data_Payload: " + remoteMessage.getData().toString());

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data_Payload: " + remoteMessage.getData().toString());
           // Log.e(TAG, "click:" + remoteMessage.getData());
           // String check = remoteMessage.getData().get("click");
            this.remoteMessage = remoteMessage;
            try {
                Map<String, String> params = remoteMessage.getData();
                JSONObject object = new JSONObject(params);
                Log.e("JSON_OBJECT_notifi", object.toString());

                JSONArray jsonArray=object.getJSONArray("posts");

                for (int i=0; i<jsonArray.length();i++){

                    JSONObject jsonObject=jsonArray.getJSONObject(i);

                    String id = jsonObject.getString("id");
                     title_msg = jsonObject.getString("title");
                    String thumbnail = jsonObject.getString("thumbnail");

                }


                    sendNotification(title_msg, "Goakhabar");

            } catch (Throwable e) {
                SetFirebaseMessage();

            }
        }

        if (remoteMessage == null)
            return;

        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification_Body: " + remoteMessage.getNotification().getBody());
            handleNotification(title_msg);
        }
    }

    private void SetFirebaseMessage() {

        try {
            Map<String, String> params = remoteMessage.getData();
            JSONObject object = new JSONObject(params);
            Log.e("JSON_Offf_notifi", object.toString());

            JSONArray jsonArray=object.getJSONArray("posts");

            for (int i=0; i<jsonArray.length();i++){

                JSONObject jsonObject=jsonArray.getJSONObject(i);

                String id = jsonObject.getString("id");
                title_msg = jsonObject.getString("title");
                String thumbnail = jsonObject.getString("thumbnail");

            }
        } catch (Throwable e) {
            SetFirebaseMessage();

        }

        sendNotificationFirebase(title_msg, "Goa khabar");


    }


    private void sendNotificationFirebase(String message, String title) {
        PendingIntent pendingIntent = null;

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("message", message);
       // intent.putExtra("userName", userName);
        intent.putExtra("title", title);
        intent.putExtra("NOTIFICATION", "NOTIFICATION");

        playNotificationSound();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        String CHANNEL_ID = "dev.news.goakhabarr";// The id of the channel.
        CharSequence name = "MyChannal";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.goakhabar)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.goakhabar);
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.goakhabar);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(1, notificationBuilder.build());

    }




    private void sendNotification(String message, String title) {
        PendingIntent pendingIntent = null;


            Intent intent = new Intent(this, MainActivity.class);
           // intent.putExtra("message", message);
            //intent.putExtra("type", type);
            //intent.putExtra("title", title);

            playNotificationSound();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);


        String CHANNEL_ID = "dev.news.goakhabarr";// The id of the channel.
        CharSequence name = "MyChannal";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(Notification.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.goakhabar);
            notificationBuilder.setColor(getResources().getColor(R.color.colorPrimary));
        } else {
            notificationBuilder.setSmallIcon(R.drawable.goakhabar);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(1, notificationBuilder.build());
    }

    /**
     * Playing notification sound
     */
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();

            /*Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmSound);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleNotification(String message) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(new Date());


        } catch (Exception e) {
            e.printStackTrace();
        }


        //new Code added
       /* if(NotificationUtils.isAppIsInBackground(getApplicationContext()))
        {
            Intent intent=new Intent(getApplicationContext().getPackageName()+"_FCM-MESSAGE");
            intent.putExtra("title",remoteMessage.getNotification().getTitle());
            intent.putExtra("message",remoteMessage.getNotification().getBody());
            LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(intent);
        }else
        {
            sendNoti();
        }
        Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
        //finish new code
*/

        //old code
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Constants.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            pushNotification.putExtra("NOTIFICATION", "NOTIFICATION");

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
            playNotificationSound();
        } else {
            sendNoti();
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
        try {
            String fcmId = json.getString("fcmId");
            String udid = json.getString("Udid");
            String userId = json.getString("userId");
            String userName = json.getString("userName");
            String message = json.getString("message");

            Intent intent = new Intent("custom-event-name");
            intent.putExtra("message", message);
            intent.putExtra("Udid", udid);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNoti() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("NOTIFICATION", "NOTIFICATION");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);
        String channelId = "Default";

        b.setAutoCancel(false);
        b.setDefaults(Notification.DEFAULT_ALL);
        b.setWhen(System.currentTimeMillis());
        b.setSmallIcon(R.drawable.goakhabar);
        b.setTicker("Hearty365");
        b.setContentTitle("Default notification");
        b.setContentText("message");
        b.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND);
        b.setContentIntent(contentIntent);
        b.setContentInfo("Info");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, b.build());
    }


}
