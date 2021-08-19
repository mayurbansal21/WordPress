package com.test;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.utilities.BaseDriver;
import com.utilities.ExcelReader;

import com.utilities.PropHandler;
import com.wordpress.pageobjects.LoginPage;
import com.wordpress.pageobjects.MyProfilePage;
import com.codoid.products.exception.FilloException;

public class MyProfilePageTest extends BaseDriver{
	private Map<String, String> myProfileData;
	private Map<String, String> menubar;
	private Map<String, String> startNewProcessData;
	private String filePath;
	private String sheetNameMyProfile;

	private String testCaseName1;
	private String testCaseName2;
	private String testCaseName3;
	private String testCaseName4;
	private String testCaseName5;
	private String testCaseName6;
	LoginPage _loginPage;
	MyProfilePage _myProfilePage;

	public MyProfilePageTest() {
		PropHandler prop = new PropHandler();
		this.filePath = System.getProperty("user.dir")+prop.getPropertyValue("TestDataLocation");
		this.sheetNameMyProfile = prop.getPropertyValue("sheetNameMyProfileData");
		this.testCaseName1 = prop.getPropertyValue("TestCaseName1");
		this.testCaseName2 = prop.getPropertyValue("TestCaseName2");
		this.testCaseName3 = prop.getPropertyValue("TestCaseName3");
		this.testCaseName4 = prop.getPropertyValue("TestCaseName4");
		this.testCaseName5 = prop.getPropertyValue("TestCaseName5");
		this.testCaseName6 = prop.getPropertyValue("TestCaseName6");
	}

	@Test(description = "Updating profile details including profile picture.")
	public void myProfilePageTestCase1(Method method) throws FilloException, IOException, InterruptedException {
		//ExtentTestManager.startTest(method.getName(),"Manage Permissions for a record");
		myProfileData = ExcelReader.getRow(filePath, sheetNameMyProfile, testCaseName1);
		_loginPage = new LoginPage(driver);
		_myProfilePage = new MyProfilePage(driver);
		Assert.assertTrue(_loginPage.login());
		Assert.assertTrue(_myProfilePage.updateProfileDetails(myProfileData));
		Assert.assertTrue(_myProfilePage.verificationOfUpdatedProfileDetails(myProfileData));
		Assert.assertTrue(_loginPage.logout());

	}
	@Test(description = "Updating profile details consists of special characters including profile picture.")
	public void myProfilePageTestCase2(Method method) throws FilloException, IOException, InterruptedException {
		//ExtentTestManager.startTest(method.getName(),"Manage Permissions for a record");
		myProfileData = ExcelReader.getRow(filePath, sheetNameMyProfile, testCaseName2);
		_loginPage = new LoginPage(driver);
		_myProfilePage = new MyProfilePage(driver);
		Assert.assertTrue(_loginPage.login());
		Assert.assertTrue(_myProfilePage.updateProfileDetails(myProfileData));
		Assert.assertTrue(_myProfilePage.verificationOfUpdatedProfileDetails(myProfileData));
		Assert.assertTrue(_loginPage.logout());

	}
	@Test(description = "Updating restricted profile picture file extension.")
	public void myProfilePageTestCase3(Method method) throws FilloException, IOException, InterruptedException {
		//ExtentTestManager.startTest(method.getName(),"Manage Permissions for a record");
		myProfileData = ExcelReader.getRow(filePath, sheetNameMyProfile, testCaseName3);
		_loginPage = new LoginPage(driver);
		_myProfilePage = new MyProfilePage(driver);
		Assert.assertTrue(_loginPage.login());
		Assert.assertTrue(_myProfilePage.updateProfileDetails(myProfileData));
		//Assert.assertTrue(_myProfilePage.verificationOfUpdatedProfileDetails(myProfileData));
		Assert.assertTrue(_loginPage.logout());

	}

	@Test(description = "Updating all field values to empty values.")
	public void myProfilePageTestCase4(Method method) throws FilloException, IOException, InterruptedException {
		//ExtentTestManager.startTest(method.getName(),"Manage Permissions for a record");
		myProfileData = ExcelReader.getRow(filePath, sheetNameMyProfile, testCaseName4);
		_loginPage = new LoginPage(driver);
		_myProfilePage = new MyProfilePage(driver);
		Assert.assertTrue(_loginPage.login());
		Assert.assertTrue(_myProfilePage.updateProfileDetails(myProfileData));
		//Assert.assertTrue(_myProfilePage.verificationOfUpdatedProfileDetails(myProfileData));
		Assert.assertTrue(_loginPage.logout());

	}
	
	@Test(description = "Adding Site url")
	public void myProfilePageTestCase5(Method method) throws FilloException, IOException, InterruptedException {
		//ExtentTestManager.startTest(method.getName(),"Manage Permissions for a record");
		myProfileData = ExcelReader.getRow(filePath, sheetNameMyProfile, testCaseName5);
		_loginPage = new LoginPage(driver);
		_myProfilePage = new MyProfilePage(driver);
		Assert.assertTrue(_loginPage.login());
		Assert.assertTrue(_myProfilePage.addUrl(myProfileData));
		//Assert.assertTrue(_myProfilePage.verificationOfUpdatedProfileDetails(myProfileData));
		Assert.assertTrue(_loginPage.logout());

	}
	
	@Test(description = "Adding site URL which already exists.")
	public void myProfilePageTestCase6(Method method) throws FilloException, IOException, InterruptedException {
		//ExtentTestManager.startTest(method.getName(),"Manage Permissions for a record");
		myProfileData = ExcelReader.getRow(filePath, sheetNameMyProfile, testCaseName5);
		_loginPage = new LoginPage(driver);
		_myProfilePage = new MyProfilePage(driver);
		Assert.assertTrue(_loginPage.login());
		Assert.assertTrue(_myProfilePage.addUrl(myProfileData));
		//Assert.assertTrue(_myProfilePage.verificationOfUpdatedProfileDetails(myProfileData));
		Assert.assertTrue(_loginPage.logout());

	}
	
	@Test(description = "Deleting site URL.")
	public void myProfilePageTestCase7(Method method) throws FilloException, IOException, InterruptedException {
		//ExtentTestManager.startTest(method.getName(),"Manage Permissions for a record");
		myProfileData = ExcelReader.getRow(filePath, sheetNameMyProfile, testCaseName6);
		_loginPage = new LoginPage(driver);
		_myProfilePage = new MyProfilePage(driver);
		Assert.assertTrue(_loginPage.login());
		Assert.assertTrue(_myProfilePage.deleteUrl(myProfileData));
		//Assert.assertTrue(_myProfilePage.verificationOfUpdatedProfileDetails(myProfileData));
		Assert.assertTrue(_loginPage.logout());

	}

}

