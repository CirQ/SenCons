package me.cirq.sencons.service;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.util.Log;

import me.cirq.sencons.SenConsApplication;

public class SensorBinder extends Binder {
    private String TAG;
    private Context context;
    private Intent intent;

    public SensorBinder(String tag, Class<?> serviceClass){
        TAG = tag;
        intent = new Intent(SenConsApplication.getContext(), serviceClass);
    }

    public void startService(){
        if(context == null) {
            Log.d(TAG, "service start in bind");
            context = SenConsApplication.getContext();
            context.startService(intent);
        }
    }

    public void stopService(){
        if(context != null) {
            Log.d(TAG, "service stop in bind");
            context.stopService(intent);
            context = null;
        }
    }

    public boolean isStopped(){
        return context == null;
    }
}
