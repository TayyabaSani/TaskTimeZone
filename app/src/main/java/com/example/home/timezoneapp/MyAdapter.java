package com.example.home.timezoneapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class MyAdapter extends ArrayAdapter<TimeZoneData>{

    List<TimeZoneData> objects = null;
    Context C;
    public MyAdapter(Context context, int textViewResourceId, List<TimeZoneData> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        this.C = context;
    }
    @Override
    public int getCount() {
        return ((null != objects) ? objects.size() : 0);
    }

    @Override
    public TimeZoneData getItem(int position) {
        return ((null != objects) ? objects.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (null == view) {
            LayoutInflater vi = (LayoutInflater) C.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.timezoneview, null);
        }

        TimeZoneData data = objects.get(position);
        if (null != data) {
            TextView textName = (TextView) view.findViewById(R.id.timezone_name);
            TextView textTime = (TextView) view.findViewById(R.id.timezone_time);
            textName.setText(data.name);
            textTime.setText(data.time);
        }

        return view;
    }
}