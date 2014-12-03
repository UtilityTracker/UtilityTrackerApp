package com.outagereporter.utilitytracker;

/**
 * Created by Miles on 12/1/2014.
 */
public class Report {
    public double lattitude;
    public double longitude;
    public String type;
    public int reportingUser;
    public int resolved;
    public int startDateUTC;
    public int resolutionDateUTC;
    public int typeSpecificUniqueID;


    Report(double newLatitude, double newLongitude, int user, String newType, int newResolved, int newStartDateUTC, int newresolutionDateUTC, int newTypeSpecificUniqueID){
        lattitude = newLatitude;
        longitude = newLongitude;
        type = newType;
        reportingUser = user;
        resolved = newResolved;
        startDateUTC = newStartDateUTC;
        resolutionDateUTC = newresolutionDateUTC;
        typeSpecificUniqueID = newTypeSpecificUniqueID;

    }
    public void updateResolution(int newresolutionDateUTC){
        resolutionDateUTC = newresolutionDateUTC;
    }

}
