package com.gacpedromediateam.primus.gachymnal.Http;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by micheal on 25/11/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
