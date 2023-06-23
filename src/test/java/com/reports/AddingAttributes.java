package com.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.awt.*;
import java.io.File;

public class AddingAttributes {
    public static void main(String[] args) {
        ExtentReports extent = new ExtentReports();

        //Create an extentSparkReporter object with a file
        File file = new File("spark.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);

        //Attaching the reporter engine with the sparkReporter
        extent.attachReporter(sparkReporter);

        extent.createTest("Test 1", "Adding author, category, and device")
                .assignAuthor("Siweh")
                        .assignCategory("Automating")
                                .assignDevice("Chrome")
                                        .fail("This is a failed test");

        extent.createTest("Test 2", "Adding author, category, and device")
                .assignAuthor("Sbu")
                .assignCategory("Manual")
                .assignDevice("Edge")
                .pass("This is a passed test");

        //Closing the report
        extent.flush();
        try {
            Desktop.getDesktop().browse(new File("spark.html").toURI());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
