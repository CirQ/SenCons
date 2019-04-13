package me.cirq.sencons.service;

import android.hardware.Sensor;

public class PressureService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = PressureService.class.getName();
        super.onCreate();
        mType = Sensor.TYPE_PRESSURE;
        mSensor = mSensorManager.getDefaultSensor(mType);
        mBinder = new SensorBinder(TAG, PressureService.class);
    }

}
