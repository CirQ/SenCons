package me.cirq.sencons.service;

import android.hardware.Sensor;

public class MagnetismService extends BaseSensorService {

    @Override
    public void onCreate(){
        TAG = MagnetismService.class.getName();
        super.onCreate();
        mType = Sensor.TYPE_MAGNETIC_FIELD;
        sensor = sensorManager.getDefaultSensor(mType);
        mBinder = new SensorBinder(TAG, MagnetismService.class);
    }

}
