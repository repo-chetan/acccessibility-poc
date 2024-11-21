package com.prac.tests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.deque.axe.AXE;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AccessibilityTest {
	
	WebDriver driver;
	private static final URL scriptUrl = AccessibilityTest.class.getResource("/axe.min.js");
	
	@BeforeMethod
	public void setup()
	{
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.flipkart.com/");
	}
	
	@Test
	public void flipkartTest() throws IOException
	{
		JSONObject responseJson = new AXE.Builder(driver,scriptUrl).analyze();
		JSONArray violations = responseJson.getJSONArray("violations");
		
		if(violations.length() == 0)
		{
			System.out.println("no errors");
		}
		else
		{
			AXE.writeResults("FlikpartResult", responseJson);
			String prettyJson  = AXE.report(violations);
			 BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\CHETAN_KATARIA\\eclipse-workspace\\AccessibilityPOCTest\\results.txt"));
			 writer.write(prettyJson);
			 writer.close();
			 
			
		}
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	

}
