package com.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class Reports {
    public static void main(String[] args) {
        ExtentReports extent = new ExtentReports();
        //Create an extentSparkReporter object with a path
//        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("C:\\Users\\sanem\\development\\selenium-with-java\\ExtentReports\\spark.html");

        //Create an extentSparkReporter object with a file
        File file = new File("spark.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);
        //Attaching the reporter engine with the sparkReporter
        extent.attachReporter(sparkReporter);

        //creating tests with different log levels
//        ExtentTest test1 = extent.createTest("Test1");
//        test1.pass("Test passed");
//
//        ExtentTest test2 = extent.createTest("Test2");
//        test2.log(Status.FAIL, "Test failed");
//
//        ExtentTest test4 = extent.createTest("Test 4");
//        test4.log(Status.WARNING, "test has a warning");
//
//        extent.createTest("Test3").skip("Test skipped");


        //Log different types of information to the extent reports
//        extent.createTest("Text based test").log(Status.PASS, "<b>Test pass</b>");

        String jsonData = "{\"menu\": {\n" +
                "  \"id\": \"file\",\n" +
                "  \"value\": \"File\",\n" +
                "  \"popup\": {\n" +
                "    \"menuitem\": [\n" +
                "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" +
                "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" +
                "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" +
                "    ]\n" +
                "  }\n" +
                "}}";

        String xmlData = "<menu id=\"file\" value=\"File\">\n" +
                "  <popup>\n" +
                "    <menuitem value=\"New\" onclick=\"CreateNewDoc()\" />\n" +
                "    <menuitem value=\"Open\" onclick=\"OpenDoc()\" />\n" +
                "    <menuitem value=\"Close\" onclick=\"CloseDoc()\" />\n" +
                "  </popup>\n" +
                "</menu>\n";

        extent.createTest("XML format test")
                .info(MarkupHelper.createCodeBlock(xmlData, CodeLanguage.XML));

        extent.createTest("Json format test")
                .log(Status.INFO, MarkupHelper.createCodeBlock(jsonData, CodeLanguage.JSON));

        //Now creating with ordered and unordered list
        List<String> listData = new ArrayList<>();
        listData.add("Summer");
        listData.add("Winter");
        listData.add("Autumn");

        Map<Integer, String> mapData = new HashMap<>();
        mapData.put(101, "Summer");
        mapData.put(102, "Winter");
        mapData.put(103, "Automn");

        extent.createTest("List based test")
                .info(MarkupHelper.createOrderedList(listData))
                .info(MarkupHelper.createUnorderedList(listData));

        extent.createTest("Map based test")
                .info(MarkupHelper.createOrderedList(mapData))
                .info(MarkupHelper.createUnorderedList(mapData));

        extent.createTest("Highlighted message test")
                .info(MarkupHelper.createLabel("This is the highlighted message", ExtentColor.INDIGO));

//        extent.createTest("Json format test").log(Status.INFO, jsonData);
        //Closing the report
        extent.flush();
        try {
            Desktop.getDesktop().browse(new File("spark.html").toURI());
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
