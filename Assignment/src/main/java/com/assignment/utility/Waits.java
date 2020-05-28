package com.assignment.utility;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assignment.resourceManager.ConfigurationReader;

public class Waits {
	
	//static WebDriverWait wait = null;
	
	static Wait<WebDriver> wait;
	static boolean elementVisible;
	static long jqueryStatus;
	static int pageTimeout;
	static String pageLoadStatus;
	
	static JavascriptExecutor js;

public static boolean explicitWait(WebDriver driver, WebElement element, int Timeout)
{
	
	wait = new FluentWait<WebDriver>(driver)
			.withTimeout(Duration.ofSeconds(Timeout))
			.pollingEvery(Duration.ofMillis(300))
			.ignoring(NoSuchElementException.class);
	
	Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>()
	{
		public Boolean apply(WebDriver driver)
		{
			if(element.isDisplayed())
			
			   elementVisible = true;
			   return true;
		}
	};
	
	wait.until(function);
	
	return elementVisible;
	
}

public static void PageLoadStatus(WebDriver driver)
{
	//pageTimeout = Integer.parseInt(ConfigurationReader.getInstance().getPageLoadTimeout());
	
	js = ((JavascriptExecutor)driver);
	
	while(true)
	{
		pageLoadStatus = (String)js.executeScript("return document.readyState");
		 
		// System.out.println("Page Loading status is: "+pageLoadStatus);
		 
		if (pageLoadStatus.equals("complete"))
		{
			System.out.println("Page Loading status is: "+pageLoadStatus);
			
			js=null;
			
			break;
		}
		
	}
}
 
}
