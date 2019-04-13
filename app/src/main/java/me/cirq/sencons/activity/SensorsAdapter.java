package me.cirq.sencons.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.cirq.sencons.R;

public class SensorsAdapter extends ArrayAdapter<SensorItem> {
    private TextView mText;
    private ImageView mImage;
    private TextView mValue0;
    private TextView mValue1;
    private TextView mValue2;

    public SensorsAdapter(Context context, int resourceId, int textViewResourceId, List<SensorItem> objects){
        super(context, resourceId, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        SensorItem sensor = getItem(position);

        if(sensor != null) {
            mText = view.findViewById(R.id.sensor_name);
            mText.setText(sensor.getName());
            mImage = view.findViewById(R.id.sensor_image);
            mImage.setImageResource(sensor.getImageId());
            mValue0 = view.findViewById(R.id.values0);
            mValue0.setText(sensor.getValue(0));
            mValue1 = view.findViewById(R.id.values1);
            mValue1.setText(sensor.getValue(1));
            mValue2 = view.findViewById(R.id.values2);
            mValue2.setText(sensor.getValue(2));
        }

        return view;
    }
}
