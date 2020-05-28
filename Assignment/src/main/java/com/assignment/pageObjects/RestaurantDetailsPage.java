package com.assignment.pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.assignment.utility.TestUtilities;
import com.assignment.utility.Waits;

public class RestaurantDetailsPage {

	WebDriver driver = null;
	Actions actions= null;
	boolean pageLoadStatus=false;
	
	public RestaurantDetailsPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public void displayTitle()
	{
		System.out.println("In Verify Title method");
		 
		System.out.println("The title of the page is: "+driver.getTitle());
	}
	
	public void verifyMenuItems()
	{
		
		TestUtilities.pageScroll(driver);
		System.out.println("Successfully scroll the page to see the menu-items");
		
	}
	
	public void closeTab()
	{
	
		TestUtilities.closeBrowserTab(driver);
		System.out.println("Succussfully switchback to Parent Tab");
		
	}
}
