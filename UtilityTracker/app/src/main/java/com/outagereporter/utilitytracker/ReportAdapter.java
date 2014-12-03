package com.outagereporter.utilitytracker;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Miles on 12/3/2014.
 */
public class ReportAdapter extends ArrayAdapter {

    private final Context context;
    private ArrayList<Report> reportList;
    private static LayoutInflater inflater = null;

    public ReportAdapter (Context context, int textViewResourceId, ArrayList<Report> _reportList){
        super(context, textViewResourceId, _reportList);
        this.context = context;
        try{

            this.reportList = _reportList;


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

        // 4. Set the text for textView
        labelView.setText(reportList.get(position).type);
        valueView.setText(reportList.get(position).lattitude + "" + reportList.get(position).longitude);

        // 5. retrn rowView
        return rowView;
    }






}
