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
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


public class outageDatabase {

    SQLiteDatabase db;
    Context context;

    outageDatabase(Context tempContext) {
        context = tempContext;
        db = context.openOrCreateDatabase("UtilityTracker", Context.MODE_PRIVATE, null);
        createDatabaseIfNeeded();
    }

    private void createDatabaseIfNeeded() {
        SQLiteDatabase db = context.openOrCreateDatabase("UtilityTracker", Context.MODE_PRIVATE, null);

        Cursor sqlResult;

        try { // This is a TERRIBLE way to tell whether or not the tables already exist. FIND ANOTHER WAY!
            sqlResult = db.rawQuery("SELECT * FROM Electricity", null);

            //Toast.makeText(context.getApplicationContext(), "Tables Already Exist\nWiping and putting new data for data debugging", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(context.getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(context.getApplicationContext(), "Created Tables: Internet, Electricity, Water, Gas, Phone", Toast.LENGTH_LONG).show();
            }
            else {
                Log.d("Debug", "Unknown SQL Error found. " + e.toString());
                return;
            }
        }
    }



    public ArrayList<MarkerOptions> getInternetMarkers() {
        return getInternetMarkers(-1);
    }
    public ArrayList<MarkerOptions> getInternetMarkers(int userID) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // INTERNET
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Internet", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Internet WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Internet").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
        return markers;
    }
    public ArrayList<MarkerOptions> getInternetMarkers(boolean unresolvedOnly) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // INTERNET
        if (unresolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Internet WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Internet", null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Internet").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
        return markers;
    }
    public ArrayList<MarkerOptions> getElectricityMarkers() {
        return getElectricityMarkers(-1);
    }
    public ArrayList<MarkerOptions> getElectricityMarkers(int userID) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // ELECTRICITY
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Electricity", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Electricity WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Electricity").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }
        return markers;
    }
    public ArrayList<MarkerOptions> getElectricityMarkers(boolean unresolvedOnly) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // ELECTRICITY
        if (unresolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Electricity WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Electricity", null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Electricity").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }
        return markers;
    }
    public ArrayList<MarkerOptions> getWaterMarkers() {
        return getWaterMarkers(-1);
    }
    public ArrayList<MarkerOptions> getWaterMarkers(int userID) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // WATER
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Water", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Water WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Water").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        return markers;
    }
    public ArrayList<MarkerOptions> getWaterMarkers(boolean unresolvedOnly) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // WATER
        if (unresolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Water WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Water", null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Water").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        return markers;
    }
    public ArrayList<MarkerOptions> getGasMarkers() {
        return getGasMarkers(-1);
    }
    public ArrayList<MarkerOptions> getGasMarkers(int userID) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // GAS
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Gas", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Gas WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Gas").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
        return markers;
    }
    public ArrayList<MarkerOptions> getGasMarkers(boolean unresolvedOnly) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // GAS
        if (unresolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Gas WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Gas", null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Gas").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
        return markers;
    }
    public ArrayList<MarkerOptions> getPhoneMarkers() {
        return getPhoneMarkers(-1);
    }
    public ArrayList<MarkerOptions> getPhoneMarkers(int userID) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // PHONE
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Phone", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Phone WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Phone").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        }
        return markers;
    }
    public ArrayList<MarkerOptions> getPhoneMarkers(boolean resolvedOnly) {
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        Cursor sqlQuery;
        // PHONE
        if (resolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Phone WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Phone", null);
        }
        while (sqlQuery.moveToNext()) {
            markers.add(new MarkerOptions().position(new LatLng(sqlQuery.getDouble(1), sqlQuery.getDouble(2))).title("Phone").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        }
        return markers;
    }


    public ArrayList<Report> getInternetArray() {
        return getInternetArray(false);
    }
    public ArrayList<Report> getInternetArray(boolean unresolvedOnly) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (unresolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Internet WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Internet", null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Internet", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }
    public ArrayList<Report> getInternetArray(int userID) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Internet", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Internet WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Internet", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }
    public ArrayList<Report> getElectricityArray() {
        return getElectricityArray(false);
    }
    public ArrayList<Report> getElectricityArray(boolean unresolvedOnly) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (unresolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Electricity WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Electricity", null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Electricity", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }
    public ArrayList<Report> getElectricityArray(int userID) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Electricity", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Electricity WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Electricity", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }
    public ArrayList<Report> getWaterArray() {
        return getWaterArray(false);
    }
    public ArrayList<Report> getWaterArray(boolean unresolvedOnly) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (unresolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Water WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Water", null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Water", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }
    public ArrayList<Report> getWaterArray(int userID) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Water", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Water WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Water", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }
    public ArrayList<Report> getGasArray() {
        return getGasArray(false);
    }
    public ArrayList<Report> getGasArray(boolean unresolvedOnly) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (unresolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Gas WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Gas", null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Gas", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }
    public ArrayList<Report> getGasArray(int userID) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Gas", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Gas WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Gas", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }
    public ArrayList<Report> getPhoneArray() {
        return getPhoneArray(false);
    }
    public ArrayList<Report> getPhoneArray(boolean unresolvedOnly) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (unresolvedOnly) {
            sqlQuery = db.rawQuery("SELECT * FROM Phone WHERE resolution = 0", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Phone", null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Phone", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }
    public ArrayList<Report> getPhoneArray(int userID) {
        ArrayList<Report> reports = new ArrayList<Report>();
        Cursor sqlQuery;
        // INTERNET
        if (userID == -1) {
            sqlQuery = db.rawQuery("SELECT * FROM Phone", null);
        }
        else {
            sqlQuery = db.rawQuery("SELECT * FROM Phone WHERE userID = " + userID, null);
        }
        while (sqlQuery.moveToNext()) {
            reports.add(new Report(sqlQuery.getColumnIndex("latitude"), sqlQuery.getColumnIndex("longitude"), sqlQuery.getColumnIndex("userID"), "Phone", sqlQuery.getColumnIndex("resolution"), sqlQuery.getColumnIndex("startDateUTC"), sqlQuery.getColumnIndex("resolutionDateUTC"), sqlQuery.getColumnIndex("outageID")));
        }
        return reports;
    }


    public boolean insertReport(String outageType, Double latitude, Double longitude, int userID) {

        outageType = outageType.toLowerCase();
        switch (outageType) {
            case "internet":
                outageType = "Internet";
                break;
            case "electricity":
                outageType = "Electricity";
                break;
            case "water":
                outageType = "Water";
                break;
            case "gas":
                outageType = "Gas";
                break;
            case "phone":
                outageType = "Phone";
                break;
            default:
                return false;
        }

        long startDateUTC = System.currentTimeMillis();

        db.execSQL("INSERT INTO " + outageType + " (latitude, longitude, userID, resolution, startDateUTC, resolutionDateUTC)" +
                "   VALUES (" + latitude + ", " + longitude + ", " + userID + ", 0, " + startDateUTC + ", -1);");

        return true;
    }






    public void insertSampleData() {  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////// REMOVE THIS WHEN TESTING IS OVER
        // DROP ALL DATA, ADD BACK FRESH
        // COLUMN INDICES: [ outageID:0 , latitude:1 , longitude:2 , userID:3 , resolution:4 , startDateUTC:5 , resolutionDateUTC:6 ]

        // INSERT ANY ADDITIONAL TEST PINS AT END OF EACH

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
