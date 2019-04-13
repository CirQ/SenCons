package me.cirq.sencons.service;

import android.hardware.Sensor;

public class AccelerationService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = AccelerationService.class.getName();
        super.onCreate();
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mType = Sensor.TYPE_ACCELEROMETER;
        mBinder = new SensorBinder(TAG, AccelerationService.class);
    }

}
