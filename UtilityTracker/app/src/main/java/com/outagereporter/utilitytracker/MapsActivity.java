package com.outagereporter.utilitytracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
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
    double longitude;
    double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outageDatabase db = new outageDatabase(this);
        db.insertSampleData(); ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// REMOVE THIS WHEN TESTING IS OVER
        setContentView(R.layout.activity_maps);
        MapNotificationCenterSingleton.getInstance().setMap(this);
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
        else {
            resetMarkers();
        }
    }

    public void resetMarkers() { // This should be called less frequently. Preferably only on request, or on submission of a Report.
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Last Known Location"));


        // Create instance of database manager
        outageDatabase db = new outageDatabase(this);


        // Add each array to the map by type
        SharedPreferences settings = getSharedPreferences("UtilityTrackerPreferences", 0);

        if (settings.getBoolean("internetFilter", false)) {
            addArrayListOfMarkers(db.getInternetMarkers(), mMap);
        }
        if (settings.getBoolean("electricityFilter", false)) {
            addArrayListOfMarkers(db.getElectricityMarkers(), mMap);
        }
        if (settings.getBoolean("waterFilter", false)) {
            addArrayListOfMarkers(db.getWaterMarkers(), mMap);
        }
        if (settings.getBoolean("gasFilter", false)) {
            addArrayListOfMarkers(db.getGasMarkers(), mMap);
        }
        if (settings.getBoolean("phoneFilter", false)) {
            addArrayListOfMarkers(db.getPhoneMarkers(), mMap);
        }
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        // Starting by centering the map over the device's lat/long
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        longitude = 0;
        latitude = 0;
        if (location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();

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

        resetMarkers();

    }

    private void addArrayListOfMarkers(ArrayList<MarkerOptions> markers, GoogleMap mMap) {
        // Loop here to add the array of markers to the map.
        for (int i = 0; i < markers.size(); i++) {
            mMap.addMarker(markers.get(i));
        }
    }

}
