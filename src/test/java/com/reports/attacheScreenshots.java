package com.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class attacheScreenshots {
    static WebDriver driver;
    public static void main(String[] args) {
        ExtentReports extent = new ExtentReports();

        //Create an extentSparkReporter object with a file
        File file = new File("spark.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(file);

        //Attaching the reporter engine with the sparkReporter
        extent.attachReporter(sparkReporter);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.get("https://demo.guru99.com/v3/index.php");
        String base64Code = captureScrenshot();
        String path = captureScreenshotWithFile("guru.jpg");

        //Attach screenshots to the extent reports using test level
        extent.createTest("Screenshot test 1", "This is for attaching screenshot to the test using base64")
                .info("This is a info message")
                        .addScreenCaptureFromBase64String(base64Code);

        extent.createTest("Screenshot test 2", "This is for attaching screenshot to the test using base64 along with a title")
                .info("This is a info message")
                .addScreenCaptureFromBase64String(base64Code, "guru page");

        extent.createTest("Screenshot test 3", "This is for attaching screenshot to the test using path test level")
                .info("This is a info message")
                .addScreenCaptureFromPath(path);

        extent.createTest("Screenshot test 4", "This is for attaching screenshot to the test using path test level along with a title")
                .info("This is a info message")
                .addScreenCaptureFromPath(path, "guru page");



        extent.createTest("Screenshot test 5", "This is for attaching screenshot to the test using base64 test level under the Details part")
                .info("This is a info message")
                .fail(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code).build())
                .fail(MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code, "Guru Login page").build());

        extent.createTest("Screenshot test 6", "This is for attaching screenshot to the test using path test level under the Details part")
                .info("This is a info message")
                .fail(MediaEntityBuilder.createScreenCaptureFromPath(path).build())
                .fail(MediaEntityBuilder.createScreenCaptureFromPath(path, "Guru Login page").build());

        //Closing the report
        extent.flush();
        driver.quit();
        try {
            Desktop.getDesktop().browse(new File("spark.html").toURI());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //Capture using a file name
    public static String captureScreenshotWithFile(String fileName){
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File("./Screenshots/" + fileName);

        try {
            FileUtils.copyFile(sourceFile, destinationFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return destinationFile.getAbsolutePath();
    }

    //Capture without file name
    public static String captureScrenshot(){
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        String base64Code = takesScreenshot.getScreenshotAs(OutputType.BASE64);
        return base64Code;
    }
}
