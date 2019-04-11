package me.cirq.sencons.activity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.cirq.sencons.R;

public class SensorAdapter extends ArrayAdapter<SensorItem> {
    private int resourceId;
    private int textViewResourceId;

    public SensorAdapter(Context context, int resourceId, int textViewResourceId, List<SensorItem> objects){
        super(context, resourceId, textViewResourceId, objects);
        this.resourceId = resourceId;
        this.textViewResourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        SensorItem sensor = getItem(position);

        ImageView image = view.findViewById(R.id.sensor_image);
        image.setImageResource(sensor.getImageId());

        TextView text = view.findViewById(R.id.sensor_name);
        text.setText(sensor.getName());

        return view;
    }
}
