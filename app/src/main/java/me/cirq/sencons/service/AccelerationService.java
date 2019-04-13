package me.cirq.sencons.service;

import android.hardware.Sensor;

public class AccelerationService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = AccelerationService.class.getName();
        super.onCreate();
        mType = Sensor.TYPE_LINEAR_ACCELERATION;
        sensor = sensorManager.getDefaultSensor(mType);
        mBinder = new SensorBinder(TAG, AccelerationService.class);
    }

}
