package me.cirq.sencons.service;

import android.hardware.Sensor;

public class RotationService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = RotationService.class.getName();
        super.onCreate();
        mType = Sensor.TYPE_GYROSCOPE;
        mSensor = mSensorManager.getDefaultSensor(mType);
        mBinder = new SensorBinder(TAG, RotationService.class);
    }

}
