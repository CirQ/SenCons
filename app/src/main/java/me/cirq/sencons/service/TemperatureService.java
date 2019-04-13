package me.cirq.sencons.service;

import android.hardware.Sensor;

public class TemperatureService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = TemperatureService.class.getName();
        super.onCreate();
        mType = Sensor.TYPE_AMBIENT_TEMPERATURE;
        mSensor = mSensorManager.getDefaultSensor(mType);
        mBinder = new SensorBinder(TAG, TemperatureService.class);
    }

}
