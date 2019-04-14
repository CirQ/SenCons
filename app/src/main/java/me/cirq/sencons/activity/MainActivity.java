package me.cirq.sencons.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;

import me.cirq.sencons.R;
import me.cirq.sencons.SenConsApplication;
import me.cirq.sencons.service.AccelerationService;
import me.cirq.sencons.service.GravityService;
import me.cirq.sencons.service.HumidityService;
import me.cirq.sencons.service.LightService;
import me.cirq.sencons.service.MagnetismService;
import me.cirq.sencons.service.PressureService;
import me.cirq.sencons.service.ProximityService;
import me.cirq.sencons.service.RotationService;
import me.cirq.sencons.service.TemperatureService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();

    private Button mStartBtn;
    private Button mStopBtn;

    private ListView mMainLst;
    private SensorsAdapter mMainAdapter;


    private BroadcastReceiver mReceiver;
    private BroadcastReceiver initReceiver(){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int name = intent.getIntExtra(SenConsApplication.SENSOR_TYPE, 0);
                float[] values = intent.getFloatArrayExtra(SenConsApplication.SENSOR_DATA);
                switch (name){
                    case Sensor.TYPE_GRAVITY:
                        sensors[0].setValues(values);
                        break;
                    case Sensor.TYPE_LINEAR_ACCELERATION:
                        sensors[1].setValues(values);
                        break;
                    case Sensor.TYPE_GYROSCOPE:
                        sensors[2].setValues(values);
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        sensors[3].setValues(values);
                        break;
                    case Sensor.TYPE_LIGHT:
                        sensors[4].setValues(values);
                        break;
                    case Sensor.TYPE_PROXIMITY:
                        sensors[5].setValues(values);
                        break;
                    case Sensor.TYPE_AMBIENT_TEMPERATURE:
                        sensors[6].setValues(values);
                        break;
                    case Sensor.TYPE_RELATIVE_HUMIDITY:
                        sensors[7].setValues(values);
                        break;
                    case Sensor.TYPE_PRESSURE:
                        sensors[8].setValues(values);
                        break;
                    default:
                        break;
                }
                mMainAdapter.notifyDataSetChanged();
            }
        };
    }


    private SensorItem[] sensors = {
        new SensorItem("gravity",      R.drawable.light_gravity,      R.drawable.dark_gravity,      new String[]{"x","y","z"}, "m/s²",  6, GravityService.class),
        new SensorItem("acceleration", R.drawable.light_acceleration, R.drawable.dark_acceleration, new String[]{"x","y","z"}, "m/s²",  6, AccelerationService.class),
        new SensorItem("rotation",     R.drawable.light_rotation,     R.drawable.dark_rotation,     new String[]{"x","y","z"}, "rad/s", 6, RotationService.class),
        new SensorItem("magnetism",    R.drawable.light_magnetism,    R.drawable.dark_magnetism,    new String[]{"x","y","z"}, "μT",    1, MagnetismService.class),
        new SensorItem("light",        R.drawable.light_light,        R.drawable.dark_light,        new String[]{"lux"},       "lx",    0, LightService.class),
        new SensorItem("proximity",    R.drawable.light_proximity,    R.drawable.dark_proximity,    new String[]{"d"},         "cm",    1, ProximityService.class),
        new SensorItem("temperature",  R.drawable.light_temperature,  R.drawable.dark_temperature,  new String[]{"T"},         "°C",    1, TemperatureService.class),
        new SensorItem("humidity",     R.drawable.light_humidity,     R.drawable.dark_humidity,     new String[]{"AH"},        "%",     2, HumidityService.class),
        new SensorItem("pressure",     R.drawable.light_pressure,     R.drawable.dark_pressure,     new String[]{"p"},         "hPa",   2, PressureService.class),
    };


    private void adaptMainList(){
        mMainLst.setAdapter(mMainAdapter);
        mMainLst.setOnItemClickListener((AdapterView<?> parent, View view, int i, long id) -> {
            SensorItem sensor = sensors[i];
            Log.i(TAG, "sensor "+sensor.getName()+" clicked");
            if(sensor.flipIcon()){      // the sensor switch is on
                Log.d(TAG, "connect service "+sensor.getName());
                if(!sensor.connect(this))
                    Log.e(TAG, "failed!");
            }
            else {                      // the sensor switch is off
                Log.d(TAG, "disconnect service "+ sensor.getName());
                sensor.disconnect();
            }
            mMainAdapter.notifyDataSetChanged();
        });
    }



    private SensorConnection connection = new SensorConnection();
    @Override
    public void onClick(View view){
        Log.v(TAG, "onClick invoked");
//        switch (view.getId()){
//            case R.id.button_start:
//                Log.d(TAG, "start click");
//                this.bindService(new Intent(this, AccelerationService.class),
//                                 connection,
//                                 BIND_AUTO_CREATE);
//                break;
//            case R.id.button_stop:
//                Log.d(TAG, "stop click");
//                if(connection.isRunning()) {
//                    connection.stopService();
//                    this.unbindService(connection);
//                }
//                break;
//            default:
//                break;
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate invoked");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mStartBtn = findViewById(R.id.button_start);
//        mStartBtn.setOnClickListener(this);
//        mStopBtn = findViewById(R.id.button_stop);
//        mStopBtn.setOnClickListener(this);

        mMainLst = findViewById(R.id.main_list);
        mMainAdapter = new SensorsAdapter(this, R.layout.item_sensor, R.id.sensor_name, Arrays.asList(sensors));
        adaptMainList();
    }


    @Override
    protected void onResume(){
        Log.v(TAG, "onResume invoked");
        super.onResume();
        if(mReceiver == null){
            mReceiver = initReceiver();
            IntentFilter iFilter = new IntentFilter(SenConsApplication.SENSOR_MESSAGE);
            SenConsApplication.getBroadcaster().registerReceiver(mReceiver, iFilter);
            Log.d(TAG, "receiver registered");
        }
    }


    @Override
    protected void onPause(){
        Log.v(TAG, "onPause invoked");
//        mStopBtn.callOnClick();
        for(SensorItem sensor: sensors) {
            if (sensor != null && sensor.isRunning()) {
                sensor.flipIcon();
                sensor.disconnect();
            }
        }
        if(mReceiver != null){
            try {
                unregisterReceiver(mReceiver);
            } catch (IllegalArgumentException e) {
                Log.d(TAG, "pause with exception");
            }
        }
        super.onPause();
    }
}
