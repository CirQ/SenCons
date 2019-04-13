package me.cirq.sencons.service;

import android.hardware.Sensor;

public class ProximityService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = ProximityService.class.getName();
        super.onCreate();
        mType = Sensor.TYPE_PROXIMITY;
        sensor = sensorManager.getDefaultSensor(mType);
        mBinder = new SensorBinder(TAG, ProximityService.class);
    }

}
