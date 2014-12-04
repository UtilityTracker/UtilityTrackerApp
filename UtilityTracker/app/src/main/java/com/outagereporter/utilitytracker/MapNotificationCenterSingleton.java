package com.outagereporter.utilitytracker;

/**
 * Created by Miles on 12/3/2014.
 */
public class MapNotificationCenterSingleton {
    private MapsActivity map;
    private ReportActivity reports;
    private HomeActivity home;

    private static MapNotificationCenterSingleton instance = null;
    private MapNotificationCenterSingleton(){


    }

    public static  MapNotificationCenterSingleton getInstance(){
        if(instance == null){
            instance = new MapNotificationCenterSingleton();
        }
        return instance;
    }
    public void setMap(MapsActivity newmap){
        map = newmap;
    }
    public void setReports(ReportActivity newreport){
        reports = newreport;
    }
    public void setHome (HomeActivity newhome){ home = newhome;}
    public void  update(){
        if(map != null) {
            map.resetMarkers();
        }
        if(reports != null){
            reports.refreshList();
        }
        if(home != null){
            home.refreshList();
        }
    }
}
