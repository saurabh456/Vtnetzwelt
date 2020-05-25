package com.assignment.resourceManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
	
	private static ConfigurationReader configReader = null;
	
	private Properties properties;
	
	private FileInputStream fis; 
	
	private ConfigurationReader()
	{
		properties = new Properties();
		try {
			fis = new FileInputStream("./configs/configuration.properties");
		    } 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		try {
			properties.load(fis);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ConfigurationReader getInstance()
	{
		if(configReader==null)
			configReader = new ConfigurationReader();
		
		return configReader;
	}
	
	public String getAppUrl() {
	String appUrl = properties.getProperty("appUrl");
	
	if(appUrl !=null)
		return appUrl;
	else
		throw new RuntimeException("AppUrl is not defined in the Configuration File");
	}
	
	public String getBroswerType() {
		String browserType = properties.getProperty("browserType").trim();
		
		System.out.println("BrowserType is: "+browserType);
		if(browserType !=null)
			return browserType;
		else
			throw new RuntimeException("BrowserType is not defined in the Configuration File");
		}

	public String getImplicitWaitTime() {
		String implicitWaitTime = properties.getProperty("implicitWait");
		
		if(implicitWaitTime !=null)
			return implicitWaitTime;
		else
			throw new RuntimeException("ImplicitWaitTime is not defined in the Configuration File");
		}
	
	
	 public String getPageLoadTimeout() 
	 { 
		 String pageLoadTimeout =properties.getProperty("pageLoadTimeout");
	  
	 if(pageLoadTimeout !=null) return pageLoadTimeout; else throw new
	 RuntimeException("pageLoadTimeout is not defined in the Configuration File");
	 }
	 
	
	public String getDriverPath() {
		String driverPath = properties.getProperty("driverPath").trim();
		
		if(driverPath !=null)
			return driverPath;
		else
			throw new RuntimeException("DriverPath is not defined in the Configuration File");
		}
	
	public String getDeliveryLocation() {
		String deliveryLocation = properties.getProperty("deliveryLocation").trim();
		
		if(deliveryLocation !=null)
			return deliveryLocation;
		else
			throw new RuntimeException("DeliveryLocation is not defined in the Configuration File");
		}
	
	public String getTestReportPath() {
		String testReportPath = properties.getProperty("testReportPath").trim();
		
		if(testReportPath !=null)
			return testReportPath;
		else
			throw new RuntimeException("Test-Report Path is not defined in the Configuration File");
		}
	
}
