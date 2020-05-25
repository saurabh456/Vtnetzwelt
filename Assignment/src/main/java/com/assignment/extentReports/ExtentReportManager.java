package com.assignment.extentReports;

import org.openqa.selenium.WebDriver;

import com.assignment.resourceManager.ConfigurationReader;
import com.assignment.utility.TestUtilities;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.externalconfig.model.Config;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	
	public static ExtentReports extentReporter;
	static ExtentHtmlReporter htmlReporter;
	public static ExtentTest extentTest;
	
	public static void reportSetup()
	{
		String testReportName = "TestReport"+"_"+TestUtilities.getFormattedDate()+".html";
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/TestReports/"+testReportName);
		
		htmlReporter.config().setDocumentTitle("Test Assignment");
		htmlReporter.config().setReportName("Test Execution Summary");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extentReporter = new ExtentReports();
		extentReporter.attachReporter(htmlReporter);
		
		System.out.println("TestReport Setup is now completed");
	}
	
	public static void reportFlush()
	{
		extentReporter.flush();
		System.out.println("Test Report is flushed successfully");
	}

}
