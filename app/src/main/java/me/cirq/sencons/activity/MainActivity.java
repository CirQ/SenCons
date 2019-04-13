package me.cirq.sencons.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import me.cirq.sencons.R;
import me.cirq.sencons.SenConsApplication;
import me.cirq.sencons.service.AccelerometerService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();

    private Button mStartBtn;
    private Button mStopBtn;

    private ListView mMainLst;
    private SensorsAdapter mMainAdapter;

    private AccelerometerService.SensorBinder sensorBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "service connected");
            sensorBinder = (AccelerometerService.SensorBinder)service;
            sensorBinder.startService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "service disconnected");
        }
    };


    private static BroadcastReceiver mReceiver;
    private static BroadcastReceiver initReceiver(){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String name = intent.getStringExtra(SenConsApplication.SENSOR_NAME);
                float[] values = intent.getFloatArrayExtra(SenConsApplication.SENSOR_DATA);
//                Log.v(TAG, name+" send intent: "+values[0]);

            }
        };
    }


    private static SensorItem[] sensors = {
        new SensorItem("gravity",       R.drawable.light_gravity,       R.drawable.dark_gravity,        new String[]{"x","y","z"},  "m/s²",     6),
        new SensorItem("acceleration",  R.drawable.light_acceleration,  R.drawable.dark_acceleration,   new String[]{"x","y","z"},  "m/s²",     6),
        new SensorItem("rotation",      R.drawable.light_rotation,      R.drawable.dark_rotation,       new String[]{"x","y","z"},  "rad/s",    6),
        new SensorItem("magnetism",     R.drawable.light_magnetism,     R.drawable.dark_magnetism,      new String[]{"x","y","z"},  "μT",       6),
        new SensorItem("light",         R.drawable.light_light,         R.drawable.dark_light,          new String[]{"lux","",""},  "lx",       6),
        new SensorItem("proximity",     R.drawable.light_proximity,     R.drawable.dark_proximity,      new String[]{"d","",""},    "cm",       2),
        new SensorItem("temperature",   R.drawable.light_temperature,   R.drawable.dark_temperature,    new String[]{"T","",""},    "°C",       1),
        new SensorItem("humidity",      R.drawable.light_humidity,      R.drawable.dark_humidity,       new String[]{"AH","",""},   "%",        2),
        new SensorItem("pressure",      R.drawable.light_pressure,      R.drawable.dark_pressure,       new String[]{"p","",""},    "hPa",      6),
    };


    private void adaptMainList(){
        List<SensorItem> list = Arrays.asList(sensors);
        mMainAdapter = new SensorsAdapter(this, R.layout.item_sensor, R.id.sensor_name, list);
        mMainLst.setAdapter(mMainAdapter);
        mMainLst.setOnItemClickListener((AdapterView<?> parent, View view, int i, long id) -> {
            SensorItem sensor = sensors[i];
            Log.i(TAG, "sensor "+sensor.getName()+" clicked");
            sensor.flipIcon();
            mMainAdapter.notifyDataSetChanged();
        });
    }



    @Override
    public void onClick(View view){
        Log.v(TAG, "onClick invoked");
        switch (view.getId()){
            case R.id.button_start:
                Log.d(TAG, "start click");
                this.bindService(new Intent(this, AccelerometerService.class),
                                 connection,
                                 BIND_AUTO_CREATE);
                break;
            case R.id.button_stop:
                Log.d(TAG, "stop click");
                if(sensorBinder != null && !sensorBinder.isStopped()) {
                    sensorBinder.stopService();
                    this.unbindService(connection);
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate invoked");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartBtn = findViewById(R.id.button_start);
        mStartBtn.setOnClickListener(this);

        mStopBtn = findViewById(R.id.button_stop);
        mStopBtn.setOnClickListener(this);

        mMainLst = findViewById(R.id.main_list);
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
        mStopBtn.callOnClick();
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
