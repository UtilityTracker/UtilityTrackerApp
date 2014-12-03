package com.outagereporter.utilitytracker;

/**
 * Created by Miles on 12/3/2014.
 */
public class MapNotificationCenterSingleton {
    private MapsActivity map;

    private static MapNotificationCenterSingleton instance = null;
    private MapNotificationCenterSingleton(){
        map = new MapsActivity();

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
    public void  update(){
        map.resetMarkers();
    }
}
