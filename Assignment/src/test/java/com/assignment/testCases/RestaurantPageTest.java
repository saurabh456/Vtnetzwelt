package com.assignment.testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.assignment.pageObjects.RestaurantPage;
import com.assignment.extentReports.ExtentReportManager;
import com.assignment.pageObjects.RestaurantDetailsPage;
import com.assignment.resourceManager.BrowserFactory;
import com.assignment.resourceManager.CommonTestData;
import com.assignment.resourceManager.PageObjectManager;
import com.assignment.utility.ExcelUtility;
import com.assignment.utility.TestUtilities;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class RestaurantPageTest {
  
	BrowserFactory browserFactory = null;
	WebDriver driver = null;
	
	PageObjectManager pageObjectManager = null;
	RestaurantPage restaurantPage = null;
	RestaurantDetailsPage restaurantDetailsPage = null;
	
	 @BeforeTest
	  public void setUp() 
	  {
		driver=BrowserFactory.getInstance().getDriver();
		ExtentReportManager.reportSetup();
		
	  }
	 
	@BeforeMethod
	  public void beforeMethod() 
	  {
	  }
	
	@Test(priority = 0, description = "Test Case to open 'Last Near-by Restaurant in a new tab")
	public void PopularRestaurantsTest()
	{
		
		ExtentReportManager.extentTest=ExtentReportManager.extentReporter.createTest("PopularRestaurantsTest TestCase Execution Started");

		try {
			
			pageObjectManager.getInstance().getRestaurantPage().setAddress();
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		restaurantDetailsPage = pageObjectManager.getInstance().getRestaurantPage().locateLastRestaurant();
		
		Assert.assertNotNull(restaurantDetailsPage, "Restaurant Details Page fails to load");
		
		System.out.println("Successfully clicked the last Restaurant");
		ExtentReportManager.extentTest.log(Status.INFO, "Successfully opened the last Restaurant details");
		
		restaurantDetailsPage.displayTitle();
		ExtentReportManager.extentTest.log(Status.INFO, "Title gets displayed successfully");
		
		restaurantDetailsPage.verifyMenuItems();
		ExtentReportManager.extentTest.log(Status.INFO, "Successfully scrolled the page to view the menu-items.");
		
		restaurantDetailsPage.closeTab();
		ExtentReportManager.extentTest.log(Status.INFO, "Successfully closed the Restaurant-details page");
		
	}
	
	@Test(priority = 1, description = "Test Case for 'Data-Scrapping' and verification of duplication of Restaurants")
	public void AvailableRestaurantsTest()
	{
		
		SoftAssert softAssert = new SoftAssert();
		ExtentReportManager.extentTest=ExtentReportManager.extentReporter.createTest("AvailableRestaurantsTest TestCase Execution Started");
		
		pageObjectManager.getInstance().getRestaurantPage().saveAvailableRestaurants();
		ExtentReportManager.extentTest.info("Successfully saved list of Available Restaurants in excel");
		
		boolean duplicateRestaurantsFlag = pageObjectManager.getInstance().getRestaurantPage().verifyDuplicacyOfAvailableRestaurants();

		softAssert.assertEquals(duplicateRestaurantsFlag, false, "Duplicate restaurants appears in the list of Restaurants.");
		
		if(duplicateRestaurantsFlag)
			ExtentReportManager.extentTest.log(Status.INFO, "Duplicate Restaurants appears in the list of Available Restaurants");
		else
		    ExtentReportManager.extentTest.log(Status.INFO, "No Duplicate Restaurants in the list. All Restaurants are unique.");
		
		softAssert.assertAll();
	
	}
	
  @AfterMethod
  public void getResult(ITestResult result)
  {
	  if(result.getStatus()==ITestResult.SUCCESS)
		  ExtentReportManager.extentTest.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" TestCase got Passed", ExtentColor.GREEN)); 
	  
	  else if (result.getStatus()==ITestResult.SKIP)
		  ExtentReportManager.extentTest.log(Status.SKIP,MarkupHelper.createLabel(result.getName()+" TestCase got Skipped", ExtentColor.ORANGE));
	  
	  else if (result.getStatus()==ITestResult.FAILURE)
	  {
		  ExtentReportManager.extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" TestCase got Failed", ExtentColor.RED));
		  
		  ExtentReportManager.extentTest.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable()+" TestCase got Failed", ExtentColor.RED));

		  String screenshotDirectoryPath = TestUtilities.captureScreenshot(driver, result.getName());
		  
		  try 
		  {
			ExtentReportManager.extentTest.fail("Snapshot for Failed Test Case as below: "+ExtentReportManager.extentTest.addScreenCaptureFromPath(screenshotDirectoryPath));
		  } 
		  catch (IOException e)
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  } 
	  
	  }
  }

 
  @AfterTest (alwaysRun = true)
  public void tearDown() 
  {
	  ExtentReportManager.reportFlush();
	  
	 browserFactory.getInstance().quitBrowser();
  }
}
