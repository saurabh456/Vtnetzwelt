package com.assignment.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Wait;

import com.assignment.pageObjects.RestaurantPage;
import com.assignment.resourceManager.BrowserFactory;
import com.assignment.resourceManager.PageObjectManager;

public class TestUtilities 
{
	
	static Wait<WebDriver> wait = null;
	static JavascriptExecutor js;
	static List<String> windows;
	static SimpleDateFormat dateFormat = null;
	PageObjectManager pageObjectManager = null;
	
	static int i, k, l;
	
	
	public static void pageScroll(WebDriver driver)
	{
		js = ((JavascriptExecutor)driver);
		
		  long pageHeight=(long)js.executeScript("return window.innerHeight");
		  System.out.println("Height of a single page in the document is: "+pageHeight);
		  
		  long documentHeight = (long)js.executeScript("return document.body.scrollHeight");
		  
		  long totalPages =(documentHeight/pageHeight)+1;
	  
	      System.out.println("Total pages in the document are: "+totalPages);
		
	  while(totalPages!=0)
	  {
		  js.executeScript("window.scrollBy(0,"+pageHeight+")");
		
		  try 
		  {
			Thread.sleep(2000);
		  }
		  catch (InterruptedException e) 
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		  totalPages--;
	  }
	
	  js=null;
	}
	
	
	public static Boolean restaurantsDuplicacyVerification(WebDriver driver)
	{
		    js = ((JavascriptExecutor)driver);
			Boolean flag = false;
			Boolean limitReachedFlag = false;
		
			long pageHeight=(long)js.executeScript("return window.innerHeight");
			
			//pageHeight = (pageHeight/2)+1;
		 
			System.out.println("Height of a single page in the document is: "+pageHeight);
		  
			long documentHeight = (long)js.executeScript("return document.body.scrollHeight");
		  
			long totalPages =(documentHeight/pageHeight)+1;
		  
		  System.out.println("Total pages in the document are: "+totalPages);
		  
		  int totalCountOfRestaurants = RestaurantPage.restaurantList.size();
		  
		  System.out.println("Total count of Restaurants is: "+totalCountOfRestaurants);
		
		  
		  i=0;
		  k=4;
		  l=0;
		  
		  while(totalPages !=0)
		  {
			  
			  js.executeScript("window.scrollBy(0,"+pageHeight+")");
			  
			  totalPages--;
	
			  
			  try 
			  { 
				  Thread.sleep(3000); 
			  } 
			  catch (InterruptedException e) 
			  {
				  
			  e.printStackTrace(); 
			  }
			  
			  
			  totalCountOfRestaurants = RestaurantPage.restaurantList.size();
			  
			  System.out.println("Total count of Restaurants is: "+totalCountOfRestaurants);
			 
			  if(k<totalCountOfRestaurants)
			  {
			    for(int m=i;m<k;m++) 
			    { 
				  l=k+4;
			  
			     for (int j=k+1;j<=l;j++)
			      {
			        if(j<totalCountOfRestaurants)
			        {
			
			        	System.out.println("In-comparison Restaurants name are: "+RestaurantPage.restaurantList.get(m)+" , "+RestaurantPage.restaurantList.get(j));
			  
			            if(RestaurantPage.restaurantList.get(m).equalsIgnoreCase(RestaurantPage.restaurantList.get(j))) 
			               {
			                  System.out.println(RestaurantPage.restaurantList.get(m)+" and " +RestaurantPage.restaurantList.get(j)+" : Restaurants are duplicate");
			  
			                  flag=true;
			                  break;
			               }
			            else //else of 2nd if
			            {}
			            
			        }
			        
			        else //else of first if
			        {
			            	
			         System.out.println("Restaurant's list is traversed completely");
			         limitReachedFlag=true;
			         break;
			        }
			        
			        }  //inner-loop ends
			   
			     //body of outer-loop
			  if(flag==true)
			    { 
				  System.out.println("Duplicate Restaurants in the list");
				  break;
			    }
			  
			  else if(limitReachedFlag==true)
			  {
				  break;
			  }
			  
			  else 
			  {
			  i= (++i)+k;
			  k =k+4; 
			  }
			  
			  }  //outer loops ends
			    
			}    // Main If condition(First if in the whole while loop)
			   
	    else    //else of (1st-main) if condition
	    {
		  System.out.println("List is already traveresed");
		    System.out.println("valueof k is: "+k);
	    }
			
		//body of while-loop begins
			  
		if(flag == true)	
		{
			  System.out.println("Duplcate Restaurants found in the list");
			  break;
			  
		}
		  
	   } //while loops ends
		  
		  js=null;
		  
		  return flag;
		  
	} //method ends
	
	public static void tabSwitch(WebDriver driver)
	{
		windows = new ArrayList<String>(driver.getWindowHandles());
	
	    driver.switchTo().window(windows.get(1));
	}
	
	public static void closeBrowserTab(WebDriver driver)
	{
		driver.close();
		driver.switchTo().window(windows.get(0));
		driver.switchTo().defaultContent();
	}
	
	public static void scrollToElement(WebDriver driver, WebElement element)
	{
	    js = ((JavascriptExecutor)driver);
	    
	  // WebElement element =  RestaurantPage.getAvailableRestaurantsText();
		
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		js = null;
	}
	
	
	public static String captureScreenshot(WebDriver driver, String testCaseName)
	{
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		String formattedCurrentDate = getFormattedDate();
		
		//String screenshotName = testCaseName+"_"+formattedCurrentDate;
		String screenshotTargetDirectory = System.getProperty("user.dir")+"/Screenshots/"+testCaseName+"_"+"Fail"+"_"+formattedCurrentDate+".png";
		
		try 
		{
			FileHandler.copy(src, new File(screenshotTargetDirectory));
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return screenshotTargetDirectory;
	}

	
	public static String getFormattedDate()
	{
		dateFormat = new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss");
		
		return dateFormat.format(new Date());
	}
	
	
}
