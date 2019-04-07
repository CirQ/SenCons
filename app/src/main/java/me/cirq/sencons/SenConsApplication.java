package me.cirq.sencons;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

public class SenConsApplication extends Application {
    private static final String TAG = SenConsApplication.class.getName();

    public static final String SENSOR_MESSAGE = "SENSOR_MESSAGE";
    public static final String SENSOR_NAME = "SENSOR_NAME";
    public static final String SENSOR_DATA = "SENSOR_DATA";

    private static SenConsApplication singleton;
    private static LocalBroadcastManager broadcaster;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        broadcaster = LocalBroadcastManager.getInstance(getApplicationContext());
    }

    @Override
    public void onTerminate(){
        singleton = null;
        super.onTerminate();
    }

    public static Context getContext(){
        return singleton.getApplicationContext();
    }

    public static LocalBroadcastManager getBroadcaster(){
        return broadcaster;
    }

}
