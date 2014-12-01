package com.outagereporter.utilitytracker;

import java.util.*;


/**
 * Created by Miles on 12/1/2014.
 */
public class ReportArraySingleton {
    private static ReportArraySingleton instance = null;

    private ArrayList reports;

    private ReportArraySingleton(){
        reports = new ArrayList();
        Report newReport = new Report(0.0,0.0,"",0);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);
        reports.add(newReport);



    }

    public static  ReportArraySingleton getInstance(){
        if(instance == null){
            instance = new ReportArraySingleton();
        }
        return instance;
    }
    public ArrayList getReports(){
        return this.reports;
    }
    public void addReport(Report report){
        reports.add(report);
    }

}
