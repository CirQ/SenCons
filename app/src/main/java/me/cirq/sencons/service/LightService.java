package me.cirq.sencons.service;

import android.hardware.Sensor;

public class LightService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = LightService.class.getName();
        super.onCreate();
        mType = Sensor.TYPE_LIGHT;
        mSensor = mSensorManager.getDefaultSensor(mType);
        mBinder = new SensorBinder(TAG, LightService.class);
    }

}
