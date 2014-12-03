package com.outagereporter.utilitytracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ReportActivity extends Activity {
    private ListView reportlistview;
    private outageDatabase database;
    private ArrayList<Report> reportList;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        reportlistview = (ListView) findViewById(R.id.listView);
        MapNotificationCenterSingleton.getInstance().setReports(this);

        database = new outageDatabase(this);
        settings = getSharedPreferences("UtilityTrackerPreferences", 0);
        refreshList();


    }


    public void refreshList(){

        reportList = new ArrayList<>();
        if (settings.getBoolean("internetFilter", false)) {
            reportList.addAll(database.getInternetArray(settings.getInt("userID", -1)));
        }
        if (settings.getBoolean("electricityFilter", false)) {
            reportList.addAll(database.getElectricityArray(settings.getInt("userID", -1)));
        }
        if (settings.getBoolean("waterFilter", false)) {
            reportList.addAll(database.getWaterArray(settings.getInt("userID", -1)));
        }
        if (settings.getBoolean("gasFilter", false)) {
            reportList.addAll(database.getGasArray(settings.getInt("userID", -1)));
        }
        if (settings.getBoolean("phoneFilter", false)) {
            reportList.addAll(database.getPhoneArray(settings.getInt("userID", -1)));
        }

        ArrayAdapter<Report> arrayAdapter = new ArrayAdapter<Report>(
                this,
                android.R.layout.simple_list_item_1,
                reportList
        );


        ArrayList<String> typesArray = new ArrayList<String>();
        for (int i = 0; i < reportList.size(); i++) {
            typesArray.add(reportList.get(i).type);
        }

        ArrayAdapter<Report> typesArrayAdapter = new ArrayAdapter<Report>(
                this,
                android.R.layout.simple_list_item_1,
                reportList
        );


        //reportlistview.setAdapter(arrayAdapter);

        reportlistview.setAdapter(typesArrayAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
