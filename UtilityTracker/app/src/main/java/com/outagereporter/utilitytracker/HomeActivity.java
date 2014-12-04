package com.outagereporter.utilitytracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends Activity {
    private ListView listview;
    private Context reportActivityContext;
    private ArrayList<Report> currentOutages;
    private SharedPreferences settings;
    private outageDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MapNotificationCenterSingleton.getInstance().setHome(this);
        listview = (ListView) findViewById(R.id.listView2);
        database = new outageDatabase(this);
        settings = getSharedPreferences("UtilityTrackerPreferences", 0);
        currentOutages = new ArrayList();
        reportActivityContext = this;

        refreshList();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Report reportToOpen = (Report) parent.getAdapter().getItem((int) id);
                Intent intent = new Intent().setClass(reportActivityContext, editReportActivity.class);
                Bundle b = new Bundle();
                b.putInt("outageID", reportToOpen.typeSpecificUniqueID);
                b.putString("tableToSearch", reportToOpen.type);
                intent.putExtras(b);
                startActivity(intent);
            }
        });


    }
    public void refreshList(){
        Thread thread = new Thread(){

            @Override
            public void run(){
                currentOutages = new ArrayList();
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
                        reportActivityContext,
                        android.R.layout.simple_list_item_1,
                        currentOutages
                );
                listview.setAdapter(reportAdapter);
            }


        };
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.run();

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
