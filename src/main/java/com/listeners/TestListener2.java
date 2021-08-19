package com.listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.utilities.BaseDriver;
//import com.Utilities.ExtentTestManager;
import com.utilities.PropHandler;
import com.commonmethods.CommonMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@SuppressWarnings("unused")
public class TestListener2 extends BaseDriver implements ITestListener {
	 protected static WebDriver driver;
	 public static ExtentReports reports;
	 public static ExtentTest test;
	 PropHandler prop;

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		  System.out.println("on test start");
		  test = reports.startTest(result.getMethod().getDescription());
		  test.log(LogStatus.INFO, result.getMethod().getMethodName() + "test is started");
	}
	public void onTestSuccess(ITestResult result) {
		  System.out.println("on test success");
		  reports.endTest(test);
		  test.log(LogStatus.PASS, result.getMethod().getMethodName() + "test is passed");	
	}
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		prop = new PropHandler();
		System.out.println("on test failure");
		driver = BaseDriver.driver;
		try {
		 // test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed");
		  File src= ((TakesScreenshot)driver). getScreenshotAs(OutputType. FILE);
		  System.out.println(src.toString());
		  String fileName = src.toString().substring(src.toString().lastIndexOf("\\")+1,src.toString().length());
		  System.out.println(fileName);
		  
		  File destFile = new File(System.getProperty("user.dir")+prop.getPropertyValue("ScreenshotLocation"));
		  System.out.println(destFile);
		  FileUtils.copyFileToDirectory(src, destFile);
		  // String file = test.addScreenCapture(DestFile + result.getMethod().getMethodName());
		  System.out.println(destFile.toString().concat("\\").concat(fileName));
		   test.log(LogStatus.FAIL, test.addScreenCapture((destFile.toString()).concat("\\").concat(fileName))+result.getMethod().getMethodName()+"test is failed");
		   reports.endTest(test);
		   //test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", test.addScreenCapture(file)+"test is failed");
		   //test.log(LogStatus.FAIL, result.getMethod().getMethodName() + "test is failed", result.getThrowable().getMessage());
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		  System.out.println("on test skipped");
		  reports.endTest(test);
		  test.log(LogStatus.SKIP, result.getMethod().getMethodName() + "test is skipped");
		
	}
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		System.out.println("on test sucess within percentage");
		
	}
	public void onStart(ITestContext context) {
		
		// TODO Auto-generated method stub
		  System.out.println("on start");
		  prop = new PropHandler();
      	String dateTime=CommonMethods.dateTimeGeneratorForReport();
      	String pathForReports = System.getProperty("user.dir")+prop.getPropertyValue("ReportLocation")+dateTime+".html";
      	reports = new ExtentReports(pathForReports);
		  
		//  reports = new ExtentReports(new SimpleDateFormat("yyyy-MM-dd hh-mm-ss-ms").format(new Date()) + "reports.html");
		
	}
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		  System.out.println("on finish");
		  //driver.close();
		  reports.endTest(test);
		  reports.flush();
		
	}
	}