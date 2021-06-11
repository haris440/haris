package kido.sparks.app.FcmServices;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Arrays;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FirebaseService";
    //jj
    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d(TAG,"NEW_TOKEN: "+token);
        sendRegistrationToPubNub(token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // If the application is in the foreground handle or display both data and notification FCM messages here.
        // Here is where you can display your own notifications built from a received FCM message.
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        // displayNotification(remoteMessage.getNotification().getBody());
    }
    private void sendRegistrationToPubNub(String token) {
        // Configure PubNub push notifications.

    }
}