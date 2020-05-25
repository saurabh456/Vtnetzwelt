package com.assignment.resourceManager;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;

public class BrowserFactory {

	private static BrowserFactory browserFactory = null;
	private WebDriver driver = null;
	private ConfigurationReader configReader = null;
	private ChromeOptions options = null;
	
	private BrowserFactory()
	{
		
	}
	public static BrowserFactory getInstance()
	{
		if(browserFactory==null)
			browserFactory = new BrowserFactory();
		
		  return browserFactory;
	}
	
	public WebDriver getDriver()
	{
		driver = createDriver();
		return driver;
	}
	
	private WebDriver createDriver()
	{
		configReader = ConfigurationReader.getInstance();
		
		String browserType = configReader.getBroswerType();
		
		switch(browserType) 
		{
		
		case "chrome":
			System.setProperty("webdriver.chrome.driver",configReader.getDriverPath());
			
		    ChromeOptions options = new ChromeOptions();    
		    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		    options.setExperimentalOption("useAutomationExtension", false);
		    
		    driver = new ChromeDriver(options);
			
			Reporter.log("Chrome Browser launched successfully",true);
			
			break;
			
		case "firefox":
			System.setProperty("webdriver.gecko.driver", configReader.getDriverPath());
			driver = new FirefoxDriver();
			Reporter.log("Firefox Browser launched successfully",true);
			break;
			
		default:
			options = new ChromeOptions();    
		    options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		    options.setExperimentalOption("useAutomationExtension", false);
		    
		    driver = new ChromeDriver(options);
		    Reporter.log("Chrome Browser launched successfully in default mode",true);
			
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(configReader.getPageLoadTimeout()), TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(configReader.getImplicitWaitTime()), TimeUnit.SECONDS);
		driver.get(configReader.getAppUrl());

		return driver;
		
	}
	
	public void quitBrowser()
	{
		if(driver!=null)
		{
			driver.quit();
			
			try {
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			driver=null;
			Reporter.log("Browser is now closed", true);
			Reporter.log("Driver is now successfully shut-down", true);
		}

	}
	
}

