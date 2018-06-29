package io.mapwize.app;

import android.app.Application;

import io.mapwize.mapwizeformapbox.AccountManager;


public class MapwizeApplication  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AccountManager.start(this, "e3a7d1fbb9ebbac276e6718f2f739806");
    }

}
