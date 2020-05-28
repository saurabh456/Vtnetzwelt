package com.assignment.resourceManager;

import org.openqa.selenium.WebDriver;

import com.assignment.pageObjects.RestaurantDetailsPage;
import com.assignment.pageObjects.RestaurantPage;

public class PageObjectManager {
	
	private static PageObjectManager pageObjectManager = null;
	
	WebDriver driver = null;
	RestaurantPage restaurantPage = null;
	RestaurantDetailsPage restaurantDetailsPage = null;
	
	private PageObjectManager()
	{
		driver = BrowserFactory.getInstance().getDriver();
	}
	
	public static PageObjectManager getInstance()
	{
		if(pageObjectManager==null)
			pageObjectManager = new PageObjectManager();
		
		return pageObjectManager;
	}
	
	public RestaurantPage getRestaurantPage()
	{
		if(restaurantPage == null)
			restaurantPage = new RestaurantPage(driver);
		
		return restaurantPage;
	}
	
	public RestaurantDetailsPage getRestaurantDetailsPage()
	{
		if(restaurantDetailsPage == null)
			restaurantDetailsPage = new RestaurantDetailsPage(driver);
		
		return restaurantDetailsPage;
	}

}
