package com.outagereporter.utilitytracker;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Miles on 12/3/2014.
 */
public class ReportAdapter extends ArrayAdapter {

    private final Context context;
    private ArrayList<Report> reportList;
    private Geocoder geocoder;
    private static LayoutInflater inflater = null;
    List<Address> addresses = null;

    public ReportAdapter (Context context, int textViewResourceId, ArrayList<Report> _reportList){
        super(context, textViewResourceId, _reportList);
        this.context = context;
        geocoder = GeocoderSingleton.getInstance(context).getGeocoder();
        try{

            this.reportList = _reportList;
            for (int i=0; i<reportList.size(); i++){
                try {
                    addresses = geocoder.getFromLocation(reportList.get(i).lattitude, reportList.get(i).longitude, 1);
                    reportList.get(i).address = addresses.get(0).getAddressLine(0) + " " + addresses.get(0).getAddressLine(1);
                }
                catch (Exception e){

                }

            }


            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        }
        catch (Exception e){

        }
    }
     public int getCount(){
         return reportList.size();
     }
    public Report getItem (Report position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        // 1. Create inflater
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.listviewrow, parent, false);

        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.Outage_type);
        TextView valueView = (TextView) rowView.findViewById(R.id.Location);
        valueView.setText(reportList.get(position).address);

        


        labelView.setText(reportList.get(position).type);
        // 5. retrn rowView
        return rowView;
    }






}
