package com.outagereporter.utilitytracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        latitudeBar = (SeekBar) findViewById(R.id.latitudeBar);
        latitudeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            Double newLatitude;
            Double percentage;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percentage = (double) progress / (double) 100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (percentage > 50) {
                    newLatitude = (90 * (percentage/100));
                }



                Toast.makeText(getApplicationContext(), "Percentage: " + percentage, Toast.LENGTH_LONG).show();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.outages_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.outages_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // <TESTING INSERT COMMAND>
        outageDatabase db = new outageDatabase(this);
        boolean success = db.insertReport("Internet", 33.199676, -87.566474, 1);
        if (success) {
            Toast.makeText(getApplicationContext(), "Successfully Added Test Marker!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Test Marker Failed!", Toast.LENGTH_LONG).show();
        }
        // </TESTING INSERT COMMAND>


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
