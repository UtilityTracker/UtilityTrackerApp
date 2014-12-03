package com.outagereporter.utilitytracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VerticalSeekBar;


public class createReportActivity extends Activity {

    private Button cancelButton;
    private SeekBar latitudeBar;
    private VerticalSeekBar longitudeBar;
    private Double currLatitude;
    private Double currLongitude;
    private EditText longitudeText;
    private EditText latitudeText;
    private Button submitButton;
    private outageDatabase database;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Report Outage");
        database = new outageDatabase(this);
        setContentView(R.layout.activity_create_report);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        longitudeText = (EditText) findViewById(R.id.longitudeTextField);
        latitudeText = (EditText) findViewById(R.id.latitudeTextField);
        submitButton = (Button) findViewById(R.id.submitButton);
        spinner = (Spinner) findViewById(R.id.outages_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.outages_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (location != null) {
            currLatitude = location.getLatitude();
            currLongitude = location.getLongitude();
        }
        else {
            currLatitude = (double) 0;
            currLongitude = (double) 0;
        }

        longitudeText.setText(currLongitude.toString(), null);
        latitudeText.setText(currLatitude.toString(), null);





        /*// <TESTING INSERT COMMAND>

        boolean success = database.insertReport("Internet", 33.199676, -87.566474, 1);
        if (success) {
            Toast.makeText(getApplicationContext(), "Successfully Added Test Marker!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Test Marker Failed!", Toast.LENGTH_LONG).show();
        }
        // </TESTING INSERT COMMAND>*/


        setupButtons();



    }

    private void setupButtons(){
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = latitudeText.getText().toString();
                String longitude = longitudeText.getText().toString();

                SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);
                int userID = settings.getInt("userID", -1);



                boolean success = database.insertReport(spinner.getSelectedItem().toString(),Double.parseDouble(latitude), Double.parseDouble(longitude), userID );
                if(success){
                    //Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString() + " "+latitude +" " +longitude, Toast.LENGTH_LONG).show();
                    MapNotificationCenterSingleton.getInstance().update();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "FAILED!: " + spinner.getSelectedItem().toString() + " "+latitude +" " +longitude, Toast.LENGTH_LONG).show();
                }



            }
        });




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_report, menu);
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
