package com.example.home.timezoneapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
public class TimeZoneAppActivity extends AppCompatActivity {

// -------------------Variable Declaration-----------------
    MyAdapter timezoneAdaptor = null;
    List<TimeZoneData> timezonelist = null;
    List<TimeZoneData> Filtertimezonelist = null;
    EditText ed;
    ListView lv;
    Button bt;
    public int LastPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_zone_app);

//-------------------Variable Initialization-----------------
        initialization();

//-------------------Set MyAdapter to ListView-----------------
        timezoneAdaptor = new MyAdapter(this, R.layout.timezoneview, timezonelist);
        lv.setAdapter(timezoneAdaptor);


//-------------------ListView OnClickMethod-----------------
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> arg0,
                                    View arg1,
                                    int arg2,
                                    long arg3) {
                if(Filtertimezonelist.isEmpty())
                {
                      String Name = timezonelist.get(arg2).name;
                      String Time = timezonelist.get(arg2).time;
                        Start_Intent(Name,Time);
                }
                else
                {
                    String Name = Filtertimezonelist.get(arg2).name;
                    String Time = Filtertimezonelist.get(arg2).time;
                    Start_Intent(Name,Time);
                }

            }
        });
//-------------------Button OnClickMethod-----------------
       bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String SearchText = ed.getText().toString();
                Toast.makeText(getApplicationContext(),SearchText,Toast.LENGTH_SHORT).show();
                int size = SearchText.length();
                if(size == 0)
                {
                    for (TimeZoneData wp : timezonelist) {
                        Filtertimezonelist.add(wp);
                        }
                   setAdapter();
                }
                else
                {
                    Filtertimezonelist.clear();
                    for (TimeZoneData wp : timezonelist) {
                        if (wp.name.toLowerCase().contains(SearchText.toLowerCase())) {
                            Filtertimezonelist.add(wp);
                        }
                    }
                    if(Filtertimezonelist.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Not Match",Toast.LENGTH_SHORT).show();
                        ed.setText("");
                        for (TimeZoneData wp : timezonelist) {
                            Filtertimezonelist.add(wp);
                        }
                    }
                    setAdapter();
                    }
            }
        });
    }
    public void initialization()
    {
        timezonelist = new ArrayList<TimeZoneData>();
        Filtertimezonelist = new ArrayList<TimeZoneData>();
        lv = (ListView) findViewById(R.id.list);
        ed = (EditText) findViewById(R.id.txtsearch);
        bt = (Button) findViewById(R.id.btSearch);
    }

//-------------------Set FilteredTimeZoneList to ListView-----------------
    public void setAdapter()
    {
        timezoneAdaptor = new MyAdapter(this, R.layout.timezoneview, Filtertimezonelist);
        lv.setAdapter(timezoneAdaptor);
    }
//-------------------Method To Pass A Data To New Activity-----------------
    public void Start_Intent(String Name, String Time)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Name",Name);
        returnIntent.putExtra("Time",Time);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        GenerateTimeZoneList();
    }
//-------------------Both Method To Generate A TimeZones-----------------
    private void GenerateTimeZoneList()
    {
        String[] listItems = TimeZone.getAvailableIDs();
        TimeZone timezone = null;
        SimpleDateFormat format = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        Date now = new Date();

        for (int index = 0; index < listItems.length; ++index) {
            timezone = TimeZone.getTimeZone(listItems[index]);
            format.setTimeZone(timezone);
            timezonelist.add(new TimeZoneData(getDiaplayName(listItems[index]), format.format(now)));
            timezone = null;
        }
    }
    private String getDiaplayName(String timezonename)
    {
        String displayname = timezonename;
        int sep = timezonename.indexOf('/');
        if (-1 != sep) {
            displayname = timezonename.substring(0, sep) + ", " + timezonename.substring(sep + 1);
            displayname = displayname.replace("_", " ");
        }
        return displayname;
    }
}




