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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VerticalSeekBar;


public class editReportActivity extends Activity {

    private Button cancelButton;
    private Button deleteButton;
    private CheckBox resolvedCheckbox;
    private Double currLatitude;
    private Double currLongitude;
    private EditText longitudeText;
    private EditText latitudeText;
    private Button submitButton;
    private outageDatabase database;
    private Spinner spinner;

    private int outageID;
    private String tableToSearch;

    private Report outageToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Report Outage");
        database = new outageDatabase(this);
        setContentView(R.layout.activity_edit_report);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        longitudeText = (EditText) findViewById(R.id.longitudeTextField);
        latitudeText = (EditText) findViewById(R.id.latitudeTextField);
        submitButton = (Button) findViewById(R.id.submitButton);
        spinner = (Spinner) findViewById(R.id.outages_spinner);
        resolvedCheckbox = (CheckBox) findViewById(R.id.resolvedCheckbox);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.outages_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        // Get outage to edit and populate existing fields

        Bundle b = getIntent().getExtras();
        outageID = b.getInt("outageID");
        tableToSearch = b.getString("tableToSearch");

        database = new outageDatabase(this);
        outageToEdit = database.getReport(tableToSearch, outageID);

        currLatitude = outageToEdit.lattitude;
        currLongitude = outageToEdit.longitude;

        switch (tableToSearch) {
            case "Internet":
                spinner.setSelection(0);
                break;
            case "Electricity":
                spinner.setSelection(1);
                break;
            case "Water":
                spinner.setSelection(2);
                break;
            case "Gas":
                spinner.setSelection(3);
                break;
            case "Phone":
                spinner.setSelection(4);
                break;
            default:
                System.exit(-1);
        }


        longitudeText.setText(currLongitude.toString(), null);
        latitudeText.setText(currLatitude.toString(), null);

        if (outageToEdit.resolved == 1) {
            resolvedCheckbox.setChecked(true);
        }



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


                outageToEdit.type = spinner.getSelectedItem().toString();
                outageToEdit.lattitude = Double.parseDouble(latitude);
                outageToEdit.longitude = Double.parseDouble(longitude);
                if (resolvedCheckbox.isChecked()) {
                    outageToEdit.resolved = 1;
                    outageToEdit.resolutionDateUTC = (int) System.currentTimeMillis();
                }

                boolean success = database.updateReport(tableToSearch, outageToEdit);
                if(success){
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                    MapNotificationCenterSingleton.getInstance().update();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "FAILED!: " + spinner.getSelectedItem().toString() + " "+latitude +" " +longitude, Toast.LENGTH_LONG).show();
                }



            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean success = database.deleteReport(tableToSearch, outageToEdit);
                MapNotificationCenterSingleton.getInstance().update();
                finish();

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
