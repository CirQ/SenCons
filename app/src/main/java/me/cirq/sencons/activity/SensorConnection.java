package me.cirq.sencons.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import me.cirq.sencons.service.SensorBinder;

public class SensorConnection implements ServiceConnection {
    private SensorBinder sensorBinder;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        sensorBinder = (SensorBinder)service;
        sensorBinder.startService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {}

    public boolean isRunning() {
        return sensorBinder!=null && !sensorBinder.isStopped();
    }

    public void stopService() {
        sensorBinder.stopService();
    }
}
