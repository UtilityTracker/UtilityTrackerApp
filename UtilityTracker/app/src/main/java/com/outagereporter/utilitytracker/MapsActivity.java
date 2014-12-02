package com.outagereporter.utilitytracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        createDatabaseIfNeeded();

        // Starting by centering the map over the device's lat/long
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        double longitude = 0;
        double latitude = 0;
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            Log.d("Debug", "Location Found! Lat: " + latitude + " Lon: " + longitude);

            // Make LatLng object to represent the found lat and long doubles
            LatLng currentLatLng;
            currentLatLng = new LatLng(latitude, longitude);
            // Make CameraUpdate object to represent the movement of the camera to LatLng and zoom of 12-ish [2.0 - 21.0](Lowest to Highest Zoom).
            CameraUpdate update;
            update = CameraUpdateFactory.newLatLngZoom(currentLatLng, 12);
            // Move mMap's camera to the CameraUpdate object's settings
            mMap.moveCamera(update);
        } else {
            Log.d("Debug", "No Last Location Known!");
        }

        // For now, add a marker to represent the LatLng found
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Last Known Location"));

        // Make an array of markers as they're being added. Will be displayed later
        List<MarkerOptions> markers = new ArrayList<MarkerOptions>();

        SQLiteDatabase db = openOrCreateDatabase("UtilityTracker", Context.MODE_PRIVATE, null);
        Cursor sqlQuery;
        // COLUMN INDICES: [ outageID:0 , latitude:1 , longitude:2 , userID:3 , resolution:4 , startDateUTC:5 , resolutionDateUTC:6 ]

        // INTERNET
        sqlQuery = db.rawQuery("SELECT * FROM Internet", null);
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Internet").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
        // ELECTRICITY
        sqlQuery = db.rawQuery("SELECT * FROM Electricity", null);
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Electricity").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }
        // WATER
        sqlQuery = db.rawQuery("SELECT * FROM Water", null);
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Water").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        // GAS
        sqlQuery = db.rawQuery("SELECT * FROM Gas", null);
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Gas").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
        // PHONE
        sqlQuery = db.rawQuery("SELECT * FROM Phone", null);
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Phone").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        }





        // Loop here to add the array of markers to the map.
        for (int i = 0; i < markers.size(); i++) {
            mMap.addMarker(markers.get(i));
        }

    }
//Toast.makeText(getApplicationContext(), "TOAST MESSAGE", Toast.LENGTH_LONG).show();
    private void createDatabaseIfNeeded() {

        SQLiteDatabase db = openOrCreateDatabase("UtilityTracker", Context.MODE_PRIVATE, null);

        Cursor sqlResult;

        try { // This is a TERRIBLE way to tell whether or not the tables already exist. FIND ANOTHER WAY!
            sqlResult = db.rawQuery("SELECT * FROM Electricity", null);
            insertSampleData(); ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// REMOVE THIS WHEN TESTING IS OVER
            Toast.makeText(getApplicationContext(), "Tables Already Exist\nWiping and putting new data for data debugging", Toast.LENGTH_LONG).show();
        }
        catch (android.database.sqlite.SQLiteException e) {

            if(e.getMessage().substring(0,13).equals("no such table")) {

                try {
                    db.execSQL("CREATE TABLE IF NOT EXISTS Internet(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");
                    db.execSQL("CREATE TABLE IF NOT EXISTS Electricity(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");
                    db.execSQL("CREATE TABLE IF NOT EXISTS Water(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");
                    db.execSQL("CREATE TABLE IF NOT EXISTS Gas(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");
                    db.execSQL("CREATE TABLE IF NOT EXISTS Phone(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");

                    insertSampleData();  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// REMOVE THIS WHEN TESTING IS OVER
                }
                catch (android.database.SQLException ex) {
                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), "Created Tables: Internet, Electricity, Water, Gas, Phone", Toast.LENGTH_LONG).show();
            }
            else {
                Log.d("Debug", "Unknown SQL Error found. " + e.toString());
                return;
            }
        }
    }

    private void insertSampleData() {  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////// REMOVE THIS WHEN TESTING IS OVER
        // DROP ALL DATA, ADD BACK FRESH
        // COLUMN INDICES: [ outageID:0 , latitude:1 , longitude:2 , userID:3 , resolution:4 , startDateUTC:5 , resolutionDateUTC:6 ]

        // INSERT ANY ADDITIONAL TEST PINS AT END OF EACH
        SQLiteDatabase db = openOrCreateDatabase("UtilityTracker", Context.MODE_PRIVATE, null);

        // INTERNET
        db.execSQL("DROP TABLE Internet");
        db.execSQL("CREATE TABLE IF NOT EXISTS Internet(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");
        db.execSQL("INSERT INTO Internet (latitude, longitude, userID, resolution, startDateUTC, resolutionDateUTC)" +
                "   VALUES (33.212332, -87.545968, 1, 0, 1500, -1);");


        // ELECTRICITY
        db.execSQL("DROP TABLE Electricity");
        db.execSQL("CREATE TABLE IF NOT EXISTS Electricity(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");
        db.execSQL("INSERT INTO Electricity (latitude, longitude, userID, resolution, startDateUTC, resolutionDateUTC)" +
                "   VALUES (33.210290, -87.544203, 1, 0, 1500, -1);");


        // WATER
        db.execSQL("DROP TABLE Water");
        db.execSQL("CREATE TABLE IF NOT EXISTS Water(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");
        db.execSQL("INSERT INTO Water (latitude, longitude, userID, resolution, startDateUTC, resolutionDateUTC)" +
                "   VALUES (33.211019, -87.544149, 1, 0, 1500, -1);");


        // GAS
        db.execSQL("DROP TABLE Gas");
        db.execSQL("CREATE TABLE IF NOT EXISTS Gas(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");
        db.execSQL("INSERT INTO Gas (latitude, longitude, userID, resolution, startDateUTC, resolutionDateUTC)" +
                "   VALUES (33.210893, -87.552912, 1, 0, 1500, -1);");


        // PHONE
        db.execSQL("DROP TABLE Phone");
        db.execSQL("CREATE TABLE IF NOT EXISTS Phone(outageID INTEGER PRIMARY KEY ASC, latitude DOUBLE, longitude DOUBLE, userID INTEGER, resolution INTEGER, startDateUTC INTEGER, resolutionDateUTC INTEGER);");
        db.execSQL("INSERT INTO Phone (latitude, longitude, userID, resolution, startDateUTC, resolutionDateUTC)" +
                "   VALUES (33.210629, -87.552958, 1, 0, 1500, -1);");


    }
}
