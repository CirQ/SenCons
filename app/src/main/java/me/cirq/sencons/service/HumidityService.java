package me.cirq.sencons.service;

import android.hardware.Sensor;

public class HumidityService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = HumidityService.class.getName();
        super.onCreate();
        mType = Sensor.TYPE_RELATIVE_HUMIDITY;
        sensor = sensorManager.getDefaultSensor(mType);
        mBinder = new SensorBinder(TAG, HumidityService.class);
    }

}
