package com.outagereporter.utilitytracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

        double tempLat;
        double tempLng;

        // Loop here to add all the markers to the array; First with just Internet, which are Azure Blue
        for (int i = 0; i < 1; i++) {
            tempLat = 33.212332;
            tempLng = -87.545968;
            markers.add(new MarkerOptions().position(new LatLng(tempLat, tempLng)).title("Internet").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        } // Pretend the following were also being added in the loop
        tempLat = 33.210290;
        tempLng = -87.544203;
        markers.add(new MarkerOptions().position(new LatLng(tempLat, tempLng)).title("Internet").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        tempLat = 33.211019;
        tempLng = -87.544149;
        markers.add(new MarkerOptions().position(new LatLng(tempLat, tempLng)).title("Internet").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        // Now Electricity - YELLOW
        for (int i = 0; i < 1; i++) {
            tempLat = 33.210893;
            tempLng = -87.552912;
            markers.add(new MarkerOptions().position(new LatLng(tempLat, tempLng)).title("Electricity").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }
        tempLat = 33.210629;
        tempLng = -87.552958;
        markers.add(new MarkerOptions().position(new LatLng(tempLat, tempLng)).title("Electricity").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        tempLat = 33.210787;
        tempLng = -87.553564;
        markers.add(new MarkerOptions().position(new LatLng(tempLat, tempLng)).title("Electricity").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));




        // Loop here to add the array of markers to the map.
        for (int i = 0; i < markers.size(); i++) {
            mMap.addMarker(markers.get(i));
        }

    }
}
