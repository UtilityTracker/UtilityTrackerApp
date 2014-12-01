package com.outagereporter.utilitytracker;

/**
 * Created by Miles on 12/1/2014.
 */
public class Report {
    public double lattitude;
    public double longitude;
    public int type;
    private String reportingUser;


    Report(double newLatitude, double newLongitude, String user, int newType){
        lattitude = newLatitude;
        longitude = newLongitude;
        type = newType;
        reportingUser = user;


    }

}
