package com.outagereporter.utilitytracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends Activity {
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listview = (ListView) findViewById(R.id.listView2);
        outageDatabase database = new outageDatabase(this);
        SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);
        ArrayList<Report> currentOutages = new ArrayList();

        if (settings.getBoolean("internetFilter", false)) {
            currentOutages.addAll(database.getInternetArray(true));

        }
        if (settings.getBoolean("electricityFilter", false)) {
            currentOutages.addAll(database.getElectricityArray(true));
        }
        if (settings.getBoolean("waterFilter", false)) {
            currentOutages.addAll(database.getWaterArray(true));
        }
        if (settings.getBoolean("gasFilter", false)) {
            currentOutages.addAll(database.getGasArray(true));
        }
        if (settings.getBoolean("phoneFilter", false)) {
            currentOutages.addAll(database.getPhoneArray(true));
        }

        ReportAdapter reportAdapter = new ReportAdapter(
                this,
                android.R.layout.simple_list_item_1,
                currentOutages
        );
        listview.setAdapter(reportAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
