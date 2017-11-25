package com.gacpedromediateam.primus.gachymnal.Http;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by micheal on 25/11/2017.
 */

public class MyFirebase extends FirebaseMessagingService {
    public String TAG = "My Message";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.e(TAG, "onMessageReceived: " + remoteMessage.getMessageType() );
        Log.e(TAG, "onMessageReceived: " + remoteMessage.getFrom());
    }
}
