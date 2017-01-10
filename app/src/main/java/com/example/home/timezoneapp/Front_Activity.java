package com.example.home.timezoneapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class Front_Activity extends AppCompatActivity {

    ListView lv;
    public int LastPosition = 0;
    MyAdapter timezoneAdaptor;
    ArrayList<TimeZoneData> AddTimeZoneList ;


// -------------------Variable Declaration-----------------
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_);

// -------------------Variable Initialization-----------------
        lv = (ListView) findViewById(R.id.list);
        AddTimeZoneList = new ArrayList<TimeZoneData>();


// -------------------ListView Method To Highlight CurrentTimeZone-----------------
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0,
                                    View arg1,
                                    int arg2,
                                    long arg3) {
                LastPosition = arg2;
                Toast.makeText(getApplicationContext(),"Current TimeZone Selected",Toast.LENGTH_LONG).show();
                arg1.setSelected(true);
            }
        });

// -------------------ListView Method To Delete Item In The List-----------------
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AddTimeZoneList.remove(i);
                lv.setAdapter(timezoneAdaptor);
                return false;
            }
        });

    }
// -------------------Method Resume Maintain A List After ComeBack To Activity-----------------
   @Override
    public void onResume()
   {
        super.onResume();
        lv.setSelection(LastPosition);
        lv.requestFocusFromTouch();
   }

// -------------------Activity Result To Receive Data-----------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == 1)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String result1=data.getStringExtra("Name");
                String result2=data.getStringExtra("Time");
                Toast.makeText(getApplicationContext(),result1+" "+result2,Toast.LENGTH_SHORT).show();
                AddTimeZoneList.add(new TimeZoneData(result1,result2));
                timezoneAdaptor = new MyAdapter(this, R.layout.timezoneview, AddTimeZoneList);
                lv.setAdapter(timezoneAdaptor);
            }
            if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(getApplicationContext(),"Not Select Any Item",Toast.LENGTH_LONG).show();
            }
        }
    }
// -------------------Intent to TimeZoneAppActivity By Click A Button-----------------
   public void AddTimeZone(View view)
   {

        Intent i = new Intent(this, TimeZoneAppActivity.class);
        startActivityForResult(i, 1);
    }
}
