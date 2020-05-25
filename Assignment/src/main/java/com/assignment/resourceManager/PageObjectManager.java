package com.assignment.resourceManager;

import org.openqa.selenium.WebDriver;

import com.assignment.pageObjects.RestaurantDetailsPage;
import com.assignment.pageObjects.RestaurantPage;

public class PageObjectManager {
	
	WebDriver driver = null;
	RestaurantPage restaurantPage = null;
	RestaurantDetailsPage restaurantDetailsPage = null;
	
	public PageObjectManager(WebDriver driver)
	{
		this.driver = driver;
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
