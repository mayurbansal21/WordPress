package com.commonmethods;

import java.io.File;
import java.sql.Driver;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.utilities.PropHandler;
import com.google.common.io.Files;
import com.listeners.TestListener2;
import com.relevantcodes.extentreports.LogStatus;
@SuppressWarnings("unused")
public class CommonMethods {

	
	/**
	 * Method for checking whether the element is present or not.
	 * 
	 * @param driver
	 * @param element
	 * @return bFlag
	 * @throws InterruptedException
	 */
	
	public static boolean IsElementDisplayed(WebDriver driver, By element) throws InterruptedException {
		try {
			if (driver.findElements(element).size() > 0) {
				if (driver.findElement(element).isDisplayed())
					return true;
				else
					return false;
			} else {
				return false;
			}
		} catch (StaleElementReferenceException e) {
			Thread.sleep(2000);
			if (driver.findElements(element).size() > 0) {
				if (driver.findElement(element).isDisplayed())
					return true;
				else
					return false;
			} else {
				return false;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}

	/**
	 * Method for getting the current date and time in a string.
	 * 
	 * @return
	 */
	public static String dateTimeGenerator(String format) {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	public static String dateTimeGeneratorForReport()
	{
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public static String dateGenerator() {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		//System.out.println(dtf.format(now));
		String currentDate = dtf.format(now);
		if(currentDate.substring(0, 1).contains("0"))
		{
			currentDate=currentDate.substring(1,currentDate.length());
			if(currentDate.substring(2, 3).contains("0"))
			{
				currentDate=currentDate.substring(0,2).concat(currentDate.substring(3,currentDate.length()));
			}
		}
		if(currentDate.substring(3, 4).contains("0"))
		{
			currentDate=currentDate.substring(0,3).concat(currentDate.substring(4,currentDate.length()));	
		}	
		return currentDate;
		//return dtf.format(now.plusDays(5));
	}

	public static String dateGeneratorInDDMMYYYY() {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		//System.out.println(dtf.format(now));
		//String currentDate = dtf.format(now);
		return dtf.format(now);
		//return dtf.format(now.plusDays(5));
	}
	
	public static String futureDateGenerator(long noOfDays)
	{
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDateTime now = LocalDateTime.now();
		//System.out.println(dtf.format(now));
		return dtf.format(now.plusDays(noOfDays));
	}
	
	public static Map<String,String> timeGenerator()
	{
		Map<String,String> map=new HashMap<String,String>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd,yyyy, hh:mm:ss a", Locale.ENGLISH);
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MMM dd,yyyy, HH:mm:ss a", Locale.ENGLISH);
		LocalDateTime now = LocalDateTime.now();
/*		System.out.println(dtf.format(now));
		System.out.println(dtf1.format(now));*/
		//return dtf.format(now.plusDays(noOfDays));
		//return dtf.format(now.plusMinutes(4));
		String twelveHourFormat = dtf.format(now);
		String twentyFourHourFormat = dtf1.format(now);
		map.put("12 hour format", twelveHourFormat);
		map.put("24 hour format", twentyFourHourFormat);
		System.out.println(map.get("24 hour format"));
		return map;
	}

	public static boolean isAlertPresent(WebDriver driver){
	    try{
	    Alert alert=driver.switchTo().alert();
	    System.out.println(alert.getText()+" Alert is Displayed");
	    return true;
	    }
	    catch(NoAlertPresentException ex){
	    System.out.println("Alert is NOT Displayed");
	    return false;
	    }
	    }

	
	public static void dynamicDropDownHandle(WebDriver driver,By element,String tagNamePassed, String selection)
	{
		WebElement table=driver.findElement(element);
		@SuppressWarnings("unchecked")
		List<WebElement> allOptions=(List<WebElement>) table.findElement(By.tagName(tagNamePassed));
		for(WebElement options:allOptions)
		{
			if(options.getText().equalsIgnoreCase(selection))
			{
				options.click();
			}
		}
		
	}
	
	public static void autoSuggestionHandle(WebDriver driver,By element, String selection)
	{
		List<WebElement> allOptions=driver.findElements(element);
		for(WebElement options:allOptions)
		{
			if(options.getText().equalsIgnoreCase(selection))
			{
				options.click();
				break;
			}
		}
		
	}
	
	public static boolean isFileDownloaded() {
		boolean bFlag=false;
		PropHandler prop = new PropHandler();  
		File dir = new File(prop.getPropertyValue("DownloadedFilesLocation"));
		  File[] dirContents = dir.listFiles();
		  if(dirContents.length==0)
		  {
			  bFlag=true;
		  }
		  else
		  {
		  for (int i = 0; i <dirContents.length; i++) {
		      //if (dirContents[i].getName().equals(fileName)) {
		          // File has been found, it can now be deleted:
		          dirContents[i].delete();
		          if(i==dirContents.length-1)
		          {
		        	  bFlag=true;
		          }
		     }
		  }
		return bFlag;
		  }
	
	/**
	 * Method to wait for page load.
	 */
    public static void waitForPageLoaded(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }
	

    
    public static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getClassName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
    }

}
