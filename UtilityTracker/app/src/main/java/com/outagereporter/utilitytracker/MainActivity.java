package com.outagereporter.utilitytracker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.Random;
public class MainActivity extends TabActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost = getTabHost();

        Intent homeIntent = new Intent().setClass(this, HomeActivity.class);
        Intent mapsIntent = new Intent().setClass(this, MapsActivity.class);
        Intent reportIntent = new Intent().setClass(this, ReportActivity.class);
        TabHost.TabSpec mapspec = tabHost.newTabSpec("Map").setIndicator("Map", null).setContent(mapsIntent);
        TabHost.TabSpec homespec = tabHost.newTabSpec("Home").setIndicator("Home", null).setContent(homeIntent);
        TabHost.TabSpec reportspec = tabHost.newTabSpec("My Reports").setIndicator("My Reports", null).setContent(reportIntent);
        tabHost.addTab(homespec);
        tabHost.addTab(mapspec);
        tabHost.addTab(reportspec);

        createSharedPrefsIfNeeded();

    }

    private void createSharedPrefsIfNeeded() {
        SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

        if (!settings.contains("internetFilter")) {
            Toast.makeText(getApplicationContext(), "internetFilter not found", Toast.LENGTH_LONG).show();
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("internetFilter", true);
            editor.putBoolean("electricityFilter", true);
            editor.putBoolean("waterFilter", true);
            editor.putBoolean("gasFilter", true);
            editor.putBoolean("phoneFilter", true);

            Random numberGenerator = new Random();
             int randomNumber = numberGenerator.nextInt((99999-10000)+1)+10000;
            editor.putInt("userID", randomNumber);


            // Commit the edits!
            editor.commit();

            outageDatabase db = new outageDatabase(this);
            db.insertSampleData();
        }

        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        if (settings.getBoolean("internetFilter", false)) {
            MenuItem internetFilter = menu.findItem(R.id.menuInternetFilter);
            internetFilter.setChecked(true);
        }
        if (settings.getBoolean("electricityFilter", false)) {
            MenuItem electricityFilter = menu.findItem(R.id.menuElectricityFilter);
            electricityFilter.setChecked(true);
        }
        if (settings.getBoolean("waterFilter", false)) {
            MenuItem waterFilter = menu.findItem(R.id.menuWaterFilter);
            waterFilter.setChecked(true);
        }
        if (settings.getBoolean("gasFilter", false)) {
            MenuItem gasFilter = menu.findItem(R.id.menuGasFilter);
            gasFilter.setChecked(true);
        }
        if (settings.getBoolean("phoneFilter", false)) {
            MenuItem phoneFilter = menu.findItem(R.id.menuPhoneFilter);
            phoneFilter.setChecked(true);
        }
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
        if (id == R.id.report){
            Intent intent = new Intent().setClass(this, createReportActivity.class);
            startActivity(intent);

            return true;
        }

        if (id == R.id.menuInternetFilter) {
            if (item.isChecked()) {
                item.setChecked(false);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("internetFilter", false);

                // Commit the edits!
                editor.commit();

            }
            else {
                item.setChecked(true);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("internetFilter", true);

                // Commit the edits!
                editor.commit();
            }
        }
        if (id == R.id.menuElectricityFilter) {
            if (item.isChecked()) {
                item.setChecked(false);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("electricityFilter", false);

                // Commit the edits!
                editor.commit();
            }
            else {
                item.setChecked(true);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("electricityFilter", true);

                // Commit the edits!
                editor.commit();
            }
        }
        if (id == R.id.menuWaterFilter) {
            if (item.isChecked()) {
                item.setChecked(false);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("waterFilter", false);

                // Commit the edits!
                editor.commit();
            }
            else {
                item.setChecked(true);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("waterFilter", true);

                // Commit the edits!
                editor.commit();
            }
        }
        if (id == R.id.menuGasFilter) {
            if (item.isChecked()) {
                item.setChecked(false);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("gasFilter", false);

                // Commit the edits!
                editor.commit();
            }
            else {
                item.setChecked(true);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("gasFilter", true);

                // Commit the edits!
                editor.commit();
            }
        }
        if (id == R.id.menuPhoneFilter) {
            if (item.isChecked()) {
                item.setChecked(false);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("phoneFilter", false);

                // Commit the edits!
                editor.commit();
            }
            else {
                item.setChecked(true);


                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("phoneFilter", true);

                // Commit the edits!
                editor.commit();
            }


        }
        MapNotificationCenterSingleton.getInstance().update();


        return super.onOptionsItemSelected(item);
    }
}
