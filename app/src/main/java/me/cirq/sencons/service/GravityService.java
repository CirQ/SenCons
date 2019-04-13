package me.cirq.sencons.service;

import android.hardware.Sensor;

public class GravityService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = GravityService.class.getName();
        super.onCreate();
        mType = Sensor.TYPE_GRAVITY;
        sensor = sensorManager.getDefaultSensor(mType);
        mBinder = new SensorBinder(TAG, GravityService.class);
    }

}
