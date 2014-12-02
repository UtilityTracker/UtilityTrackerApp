package com.outagereporter.utilitytracker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


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
        TabHost.TabSpec reportspec = tabHost.newTabSpec("Reports").setIndicator("Reports", null).setContent(reportIntent);
        tabHost.addTab(homespec);
        tabHost.addTab(mapspec);
        tabHost.addTab(reportspec);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
