package me.cirq.sencons.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import me.cirq.sencons.SenConsApplication;



public class AccelerometerService extends Service implements SensorEventListener {
    private static final String TAG = AccelerometerService.class.getName();

    protected static final boolean mLogging = true;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    private SensorBinder mBinder = new SensorBinder();


    public class SensorBinder extends Binder {
        private Context context;
        private Intent intent = new Intent(SenConsApplication.getContext(), AccelerometerService.class);

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



    @Override
    public void onCreate(){
        Log.v(TAG, "onCreate invoked");
        super.onCreate();
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
//        for(Sensor sensor: sensors){
//            Log.i(TAG, sensor.toString());
//        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.v(TAG, "onStartCommand invoked");
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy(){
        Log.v(TAG, "onDestroy invoked");
        mSensorManager.unregisterListener(this);
        stopSelf();
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "onBind invoked");
        return mBinder;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
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
        intent.putExtra(SenConsApplication.SENSOR_NAME, "acc");
        intent.putExtra(SenConsApplication.SENSOR_DATA, values);
        SenConsApplication.getBroadcaster().sendBroadcast(intent);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){}
}
