package me.cirq.sencons.activity;

import android.content.Context;
import android.content.Intent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SensorItem {

    private String name;
    private int lightImageId;
    private int darkImageId;
    private boolean dark;
    private float[] values;
    private String[] dim;
    private String unit;
    private String fmt;
    private Class<?> sensorService;

    private Context context;
    private SensorConnection connection;

    public SensorItem(String name, int lightImageId, int darkImageId, String[] dim, String unit, int round, Class<?> sensorService) {
        this.name = name.toUpperCase();
        this.lightImageId = lightImageId;
        this.darkImageId = darkImageId;
        this.dark = false;
        this.values = new float[3];
        this.dim = dim;
        this.unit = unit;
        this.fmt = String.format("%%s: %%.%df %%s", round);
        this.sensorService = sensorService;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return dark ? darkImageId : lightImageId;
    }

    public boolean flipIcon(){
        this.dark ^= true;
        return dark;
    }

    public boolean isRunning(){
        return dark;
    }

    public void setValues(float[] values){
        System.arraycopy(values, 0, this.values, 0, values.length);
    }

    public String getValue(int i){
        if(i >= dim.length)
            return "";
        return String.format(fmt, dim[i], values[i], unit);
    }

    public boolean connect(Context context) {
        this.context = context;
        this.connection = new SensorConnection();
        Intent intent = new Intent(context, sensorService);
        return context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
//        try {
//            Method hasSensorMethod = sensorService.getMethod("hasSensor");
//            return (Boolean)hasSensorMethod.invoke(null);
//        } catch (Exception e) {
//            return false;
//        }
    }

    public void disconnect() {
        if(connection!=null && connection.isRunning()) {
            connection.stopService();
            context.unbindService(connection);
            connection = null;
        }
    }
}
