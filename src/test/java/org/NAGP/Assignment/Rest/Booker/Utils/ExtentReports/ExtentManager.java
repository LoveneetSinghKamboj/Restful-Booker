package org.NAGP.Assignment.Rest.Booker.Utils.ExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;


public class ExtentManager {
    public static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports createExtentReports() {
        String sourcefileName = null;
        String strProjectLoc = System.getProperty("user.dir");
        String timeStamp = new SimpleDateFormat("d-MMM-YY HH-mm-ss").format(Calendar.getInstance().getTime());
        File dir1 = new File(STR."\{strProjectLoc}\{File.separator}src\{File.separator}test\{File.separator}resources");
        for(String s: Objects.requireNonNull(dir1.list()))
        {
            if(s.contains("Current Test Result"))
            {
                sourcefileName=s;
            }
        }
        File dir = new File(STR."\{strProjectLoc}\{File.separator}src\{File.separator}test\{File.separator}resources/"+sourcefileName);
        if (!dir.isDirectory()) {
            System.err.println("There is no directory @ given path");
        } else {
            File newDir = new File(STR."\{dir.getParent()}\{File.separator}Archived Test Result-"+sourcefileName.substring(20));
            dir.renameTo(newDir);
        }
        ExtentSparkReporter reporter = new ExtentSparkReporter(STR."\{strProjectLoc}\{File.separator}src\{File.separator}test\{File.separator}resources./Current Test Result-"+timeStamp+"/extent-report.html");
        reporter.config().setReportName("Red Bus Extent Report");
        extentReports.attachReporter(reporter);
        return extentReports;
    }
}