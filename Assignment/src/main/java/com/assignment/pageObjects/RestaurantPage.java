package com.assignment.pageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.assignment.resourceManager.CommonTestData;
import com.assignment.resourceManager.ConfigurationReader;
import com.assignment.utility.ExcelUtility;
import com.assignment.utility.TestUtilities;
import com.assignment.utility.Waits;

public class RestaurantPage {

	WebDriver driver;
	Actions actions;
	Boolean pageLoadStatus=false;
	public static List<String> restaurantList=null;
	
	
	public RestaurantPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".info___2rXBX>span")
	private WebElement deliverTo_TextField;
	
	@FindBy(css="div[class*='AddressSelector'] input#location-input")
	private WebElement enterText;    //used to enter the text
	
	@FindBy(css="ul[role='listbox']>li:nth-of-type(4)")
	private WebElement fourthSuggestion;   //used to clear the field
	
	@FindBy(xpath ="//*[@class='swiperBtnNext___2J988']") 
	private List<WebElement> rightSwipeArrowButton;
	
	
	@FindBy(css="#page-content .ant-layout [class*='swiperBoundary___2g_tJ'] .swiper-wrapper>div:nth-last-of-type(1) a")
	private WebElement lastRestaurant;
	
	@FindBy(xpath="//*[contains(text(),'Popular Near You') and @class='title___24Kho']")
	private WebElement popularNearYouHeader;
	
	@FindBy(css="[class=textAddress___Rba2a]")
	private WebElement currentDeliveryAddressTextBox;
	
	@FindBy(xpath="//*[(@class='textPrefix___8VBSV') and (contains(text(),'Available Restaurants in '))]")
	private static WebElement availableRestaurantsText;
	
	@FindBy(xpath="//*[contains(text(),'Available Restaurants in ')]/parent::h1//following-sibling::*//*[contains(@class,'ant-col-24 RestaurantListCol___1FZ8V')]")
	private List<WebElement> availableRestaurantsNameList;
	
	public static WebElement getAvailableRestaurantsText() 
	{
		return availableRestaurantsText;
	}

	final static String restaurantsList ="//*[contains(text(),'Available Restaurants in ')]/parent::h1//following-sibling::*//*[contains(@class,'ant-col-24 RestaurantListCol___1FZ8V')]";
	
	
	public static String getRestaurantsList() {
		return restaurantsList;
	}

	public void setAddress() throws InterruptedException
	{
		 Waits.PageLoadStatus(driver);
		 System.out.println("Page Loading is completed successfully");
		
		deliverTo_TextField.click();
		
		enterText.sendKeys(ConfigurationReader.getInstance().getDeliveryLocation());
			
		  if(Waits.explicitWait(driver, fourthSuggestion, 20))
		  {
		      fourthSuggestion.click();
		  	  
		  }
		  else
			  System.out.println("Element is not visible");
		
		}
	
	
	public RestaurantDetailsPage locateLastRestaurant()
	{

		  Waits.PageLoadStatus(driver);
		  System.out.println("Page Loading is completed successfully");
		  
		  CommonTestData.popularNearYouHeader=popularNearYouHeader;
		  
		//  TestUtilities.scrollToElement(driver,CommonTestData.popularNearYouHeader);
		  
		 while(rightSwipeArrowButton.size()!=0)
		 {
			
			Waits.explicitWait(driver, rightSwipeArrowButton.get(0), 5);
				
				if(rightSwipeArrowButton.get(0).isDisplayed())
				     rightSwipeArrowButton.get(0).click();
		}
		 
	    System.out.println("Right Swipe Flag is not visible");
		System.out.println("Is last Restaurant displays: "+lastRestaurant.isDisplayed());
		

		((JavascriptExecutor)driver).executeScript("arguments[0].setAttribute('target','_blank');",lastRestaurant);
		lastRestaurant.click();
		
		TestUtilities.tabSwitch(driver);
		
		Waits.PageLoadStatus(driver);
		
		return new RestaurantDetailsPage(driver);
		
	}
	
	public void saveAvailableRestaurants()
	{
		TestUtilities.pageScroll(driver);
		
		int totalAvailableRestaurants = availableRestaurantsNameList.size();
		System.out.println("Total Available Restaurants are: "+totalAvailableRestaurants);
		
		String currentDeliveryLocation=currentDeliveryAddressTextBox.getText();
		
		System.out.println("Current Delivery-Address is :"+currentDeliveryLocation);
		
		String sheetName = currentDeliveryLocation.substring(0, 5)+"_"+TestUtilities.getFormattedDate();
		
		CommonTestData.availableRestaurantsText = availableRestaurantsText;
		
		TestUtilities.scrollToElement(driver, CommonTestData.availableRestaurantsText);
		
		List<Map<String,String>> restaurantsFinalList = new ArrayList<Map<String,String>>();
		Map<String, String> restaurantsMap = new HashMap<String, String>();
		
		for(int i=1;i<=totalAvailableRestaurants;i++)
		{
			String ik = String.valueOf(i);
			
			String tempName = "["+ik+"]";
			
			String restaurantName = driver.findElement(By.xpath(restaurantsList+"["+ik+"]"+"//h6")).getText();
			
			String restaurantUrl = driver.findElement(By.xpath(restaurantsList+"["+ik+"]"+"//a")).getAttribute("href");
			
			//ExcelUtility.writeDataIntoExcel1(restaurantName,restaurantUrl, sheetName);
			
			if(i<=4)
			{
			System.out.println("Restaurant's name is: "+restaurantName);
			
			System.out.println("Retaurant's Url is: "+restaurantUrl);
			
			String nameUrlAddress = currentDeliveryLocation+"@"+restaurantName+"@"+restaurantUrl;
			System.out.println("Name and Url is: "+nameUrlAddress);
			
			restaurantsMap.put(ik,nameUrlAddress);
			
			restaurantList = new ArrayList<String>();
			
			restaurantList.add(restaurantName);
			}
			
			else
				restaurantList.add(restaurantName);
			
		}
		
		restaurantsFinalList.add(restaurantsMap);
        
		System.out.println("Successfully added the Data in the Map");
		
		System.out.println("Size of the List is: "+restaurantsFinalList.size());
		
		System.out.println("Size of the map is: "+restaurantsMap.size());
		
		ExcelUtility.writeDataIntoExcel(restaurantsFinalList, sheetName);
	}
	
	public Boolean verifyDuplicacyOfAvailableRestaurants()
	{
		// TestUtilities.moveToElement(driver);
		
		System.out.println("Calling pageScroll method to check the duplicacy of Restaurants");
		Boolean flag = TestUtilities.restaurantsDuplicacyVerification(driver);
		
		return flag;
	}
	
	}
