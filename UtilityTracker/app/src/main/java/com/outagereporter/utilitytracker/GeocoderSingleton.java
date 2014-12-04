package com.outagereporter.utilitytracker;

import android.content.Context;
import android.location.Geocoder;

import java.util.Locale;

/**
 * Created by Miles on 12/3/2014.
 */
public class GeocoderSingleton {
    private static Geocoder geocoder = null;
    private static GeocoderSingleton instance = null;
    private Context context;
    private GeocoderSingleton(Context thiscontext){
            context = thiscontext;
            geocoder = new Geocoder(context, Locale.getDefault());

    }

    public static  GeocoderSingleton getInstance(Context context){
        if(instance == null){
            instance = new GeocoderSingleton(context);
        }
        return instance;
    }

    public Geocoder  getGeocoder(){
        return geocoder;
    }
}
