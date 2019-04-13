package me.cirq.sencons.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import me.cirq.sencons.SenConsApplication;

public class BaseSensorService extends Service implements SensorEventListener {
    protected static final boolean mLogging = false;

    protected static SensorManager sensorManager;
    protected static Sensor sensor;
    protected String TAG = "";
    protected int mType;
    protected SensorBinder mBinder;
    static {
        sensorManager = (SensorManager)SenConsApplication.getContext().getSystemService(SENSOR_SERVICE);
    }


    @Override
    public void onCreate(){
        Log.v(TAG, "onCreate invoked");
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.v(TAG, "onStartCommand invoked");
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy(){
        Log.v(TAG, "onDestroy invoked");
        sensorManager.unregisterListener(this);
        stopSelf();
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onBind invoked");
        return mBinder;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;

        if(mLogging){
            StringBuilder sb = new StringBuilder("Fetch data: ");
            for(int i = 0; i < values.length; i++){
                sb.append(values[i]);
                if(i != values.length-1)
                    sb.append(" | ");
            }
            Log.v(TAG, sb.toString());
        }

        Intent intent = new Intent();
        intent.setAction(SenConsApplication.SENSOR_MESSAGE);
        intent.putExtra(SenConsApplication.SENSOR_TYPE, mType);
        intent.putExtra(SenConsApplication.SENSOR_DATA, values);
        SenConsApplication.getBroadcaster().sendBroadcast(intent);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}
}
