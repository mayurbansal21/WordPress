package com.wordpress.pageobjects;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.commonmethods.CommonMethods;
import com.listeners.TestListener2;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.PropHandler;

public class MyProfilePage {

	public WebDriver driver;

	/*
	 * My Profile Page Objects.
	 */
	private By headerMyProfile = By.xpath("//h1[contains(text(),'My') and contains(text(),'Profile')]");
	private By txtBoxFirstName = By.id("first_name");
	private By txtBoxLastName = By.id("last_name");
	private By txtBoxPublicDisplayName = By.id("display_name");
	private By txtAreaAboutMe = By.id("description");
	//private By linkClickToChangePhoto = By.xpath("//span[text()='Click to change photo']");
	private By linkClickToChangePhoto = By.className("edit-gravatar__label");
	private By btnSaveProfileDetails = By.xpath("//button[text()='Save profile details']");
	private By btnSaveProfileDetailsDisabled = By.xpath("//button[text()='Save profile details' and @disabled]");
	private By txtSavedSuccessfullyMsg = By.xpath("//span[text()='Settings saved successfully!']");
	private By txtProfilePicSavedMsg = By.xpath("//span[contains(text(),'successfully uploaded a new profile photo')]");
	private By btnChangeMyPhoto = By.xpath("//button[text()='Change My Photo']");
	private By txtErrorMsgForFileExtension = By.xpath("//span[contains(text(),'pdf files are not supported')]");
	private By chkBoxGravatar = By.id("inspector-toggle-control-0");
	private By btnAdd = By.xpath("//span[text()='Add']/ancestor::button[1]");
	private By btnAddURL = By.xpath("//button[text()='Add URL']");
	private By txtBoxURL = By.xpath("//input[@placeholder='Enter a URL']");
	private By txtBoxDescription = By.xpath("//input[@placeholder='Enter a description']");
	private By btnAddSite = By.xpath("//button[text()='Add Site']");
	private By btnAddSiteDisabled = By.xpath("//button[text()='Add Site' and @disabled]");
	private By txtAddUrlErrorMsg = By.xpath("//span[contains(text(),'That link is already in your profile links')]");
	
	public static String displayName;
	public MyProfilePage(WebDriver driver) {
		this.driver = driver;

	}

	/**
	 * Method for updating Profile Details.
	 * @return
	 */
	public boolean updateProfileDetails(Map<String,String> profileData) {
		boolean bFlag = false;		
		try {
			Robot robot = new Robot();
			//new WebDriverWait(driver, 20).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxUserName));
			new WebDriverWait(driver, 20).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxFirstName));
			new WebDriverWait(driver, 20).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxLastName));
			if(CommonMethods.IsElementDisplayed(driver, headerMyProfile))
			{
				if(Boolean.parseBoolean(profileData.get("EmptyDetailsFlag")))
				{
					driver.findElement(txtBoxFirstName).sendKeys("test");
					driver.findElement(txtBoxFirstName).clear();
					
					driver.findElement(txtBoxLastName).clear();
					displayName= driver.findElement(txtBoxPublicDisplayName).getAttribute("value");
					driver.findElement(txtBoxPublicDisplayName).clear();
					//driver.findElement(txtAreaAboutMe).sendKeys("test");
					driver.findElement(txtAreaAboutMe).clear();
					Thread.sleep(1000);
				}
				else {
				Thread.sleep(1500);
				driver.findElement(txtBoxFirstName).clear();
				driver.findElement(txtBoxFirstName).sendKeys(profileData.get("FirstName"));
				driver.findElement(txtBoxLastName).clear();
				driver.findElement(txtBoxLastName).sendKeys(profileData.get("LastName"));
				driver.findElement(txtBoxPublicDisplayName).clear();
				driver.findElement(txtBoxPublicDisplayName).sendKeys(profileData.get("PublicDisplayName"));
				driver.findElement(txtAreaAboutMe).clear();
				driver.findElement(txtAreaAboutMe).sendKeys(profileData.get("AboutMe"));
				}
				if(!(driver.findElement(By.xpath("//input[@id='inspector-toggle-control-0']/ancestor::span[1]")).getAttribute("class").equalsIgnoreCase(profileData.get("Gravatar"))))
				{
					driver.findElement(chkBoxGravatar).click();
				}
				if(Boolean.parseBoolean(profileData.get("UpdateProfilePictureFlag")))
				{
					driver.findElement(linkClickToChangePhoto).click();
					StringSelection ss = new StringSelection(System.getProperty("user.dir")+profileData.get("ProfilePicture"));
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
					Thread.sleep(1000);
					robot.keyPress(KeyEvent.VK_CONTROL);
					Thread.sleep(1000);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					Thread.sleep(1000);
					robot.keyPress(KeyEvent.VK_ENTER);
					Thread.sleep(1000);
					robot.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(1000);
					robot.keyPress(KeyEvent.VK_SHIFT);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_SHIFT);
					robot.keyRelease(KeyEvent.VK_TAB);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_A);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyRelease(KeyEvent.VK_A);
					Thread.sleep(500);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					if(Boolean.parseBoolean(profileData.get("ProfilePictureInvalidExtensionFlag")))
					{
						//Thread.sleep(2000);
						if(CommonMethods.isAlertPresent(driver))
						{
							driver.switchTo().alert().dismiss();
						}
						if(CommonMethods.IsElementDisplayed(driver, txtErrorMsgForFileExtension))
						{
							System.out.println("extension msg. gets displayed.");
							if(CommonMethods.isAlertPresent(driver))
							{
								driver.switchTo().alert().dismiss();
							}
							bFlag=true;
						}
						else
						{
							System.out.println("extension msg. is not getting displayed.");
						}
						
					}
					else {
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyPress(KeyEvent.VK_TAB);
					robot.keyRelease(KeyEvent.VK_TAB);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					if(CommonMethods.isAlertPresent(driver))
					{
						driver.switchTo().alert().dismiss();
					}
					Thread.sleep(2000);
					//Assert.assertTrue(CommonMethods.IsElementDisplayed(driver, txtProfilePicSavedMsg));
					new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(btnSaveProfileDetails));
					if(CommonMethods.IsElementDisplayed(driver, btnSaveProfileDetailsDisabled))
					{
						System.out.println("None of the profile details have been updated.");
					}
					else {
					driver.findElement(btnSaveProfileDetails).click();
					new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxFirstName));
					Thread.sleep(2000);
					if(CommonMethods.IsElementDisplayed(driver, txtSavedSuccessfullyMsg))
					{
						TestListener2.test.log(LogStatus.PASS, CommonMethods.getCurrentMethodName()+" "+"My Profile page details saved successfully.");
						bFlag=true;
					}
					else {
						TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName()+" "+"My Profile page details has not been updated.");
						System.out.println("My Profile page details has not been updated.");
					}
					}
				}
				}
				else {
				new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(btnSaveProfileDetails));
				if(CommonMethods.IsElementDisplayed(driver, btnSaveProfileDetailsDisabled))
				{
					System.out.println("None of the profile details have been updated.");
				}
				else {
				driver.findElement(btnSaveProfileDetails).click();
				if(CommonMethods.IsElementDisplayed(driver, btnSaveProfileDetails))
				{
					driver.findElement(btnSaveProfileDetails).click();
				}
				if(CommonMethods.IsElementDisplayed(driver, txtSavedSuccessfullyMsg))
				{
					TestListener2.test.log(LogStatus.PASS, CommonMethods.getCurrentMethodName()+" "+"My Profile page details saved successfully.");
					bFlag=true;
				}
				else {
					TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName()+" "+"My Profile page details has not been updated.");
					System.out.println("My Profile page details has not been updated.");
				}
				}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName(), e);
		}
		return bFlag;
	}
	
	/**
	 * Method for adding site url.
	 * @param profileData
	 * @return bFlag
	 */
	public boolean addUrl(Map<String,String> profileData)
	{
		boolean bFlag=false;
		try {
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(btnAdd));
			driver.findElement(btnAdd).click();
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(btnAddURL));
			driver.findElement(btnAddURL).click();
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxURL));
			driver.findElement(txtBoxURL).sendKeys(profileData.get("URL"));
			driver.findElement(txtBoxDescription).sendKeys(profileData.get("Description"));
			if(CommonMethods.IsElementDisplayed(driver, btnAddSiteDisabled))
			{
				TestListener2.test.log(LogStatus.PASS, "Add Site button is displaying as disabled.");
			}
			else
			{
				driver.findElement(btnAddSite).click();
				if((CommonMethods.IsElementDisplayed(driver, By.xpath("//span[text()=\""+profileData.get("Description")+"\"]"))))
				{
					bFlag=true;
					TestListener2.test.log(LogStatus.PASS, "Added URL has been displayed successfully.");			
				}
				else if(CommonMethods.IsElementDisplayed(driver, txtAddUrlErrorMsg))
				{
					bFlag=true;
					TestListener2.test.log(LogStatus.PASS, "Added URL is already present.");			
				}
				else
				{
					TestListener2.test.log(LogStatus.FAIL, "Added URL has not been displayed.");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName(), e);
		}
		return bFlag;
	}
	
	/**
	 * Method for deleting site url.
	 * @param profileData
	 * @return bFlag
	 */
	public boolean deleteUrl(Map<String,String> profileData)
	{
		boolean bFlag=false;
		try {
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(btnAdd));
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()=\""+profileData.get("Description")+"\"]/following::button[contains(@class,'link__remove')]")));
			driver.findElement(By.xpath("//span[text()=\""+profileData.get("Description")+"\"]/following::button[contains(@class,'link__remove')]")).click();
			if(CommonMethods.IsElementDisplayed(driver, By.xpath("//span[text()=\""+profileData.get("Description")+"\"]")))
			{
				bFlag=true;
				TestListener2.test.log(LogStatus.PASS, "URL has been removed successfully.");				
			}
			else
			{
				TestListener2.test.log(LogStatus.FAIL, "URL has not been removed.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName(), e);
		}
		return bFlag;
	}
	
	/**
	 * Method for verification of updated profile details.
	 * @param profileData
	 * @return
	 */
	public boolean verificationOfUpdatedProfileDetails(Map<String,String> profileData) {
		boolean bFlag = false;
		try {
			driver.navigate().refresh();
			Thread.sleep(2000);
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxFirstName));
			new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxLastName));
			if(Boolean.parseBoolean(profileData.get("EmptyDetailsFlag")))
			{
				if((driver.findElement(txtBoxFirstName).getAttribute("value")==null ||driver.findElement(txtBoxFirstName).getAttribute("value").isEmpty())
						&&(driver.findElement(txtBoxLastName).getAttribute("value")==null ||driver.findElement(txtBoxLastName).getAttribute("value").isEmpty())
						&&(driver.findElement(txtAreaAboutMe).getAttribute("value")==null ||driver.findElement(txtAreaAboutMe).getAttribute("value").isEmpty())
						&&(driver.findElement(txtBoxPublicDisplayName).getAttribute("value").equalsIgnoreCase(displayName)))
				{
					TestListener2.test.log(LogStatus.PASS, CommonMethods.getCurrentMethodName()+" "+"My Profile page details have been updated successfully.");
					bFlag=true;
				}
				else
				{
					TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName()+" "+"My Profile page details have not been updated after saving details.");
				}
			}
			else {
			System.out.println(driver.findElement(txtBoxFirstName).getAttribute("value")+"      "+profileData.get("FirstName"));
			Assert.assertEquals(driver.findElement(txtBoxFirstName).getAttribute("value"),profileData.get("FirstName"));
			Assert.assertEquals(driver.findElement(txtBoxLastName).getAttribute("value"),profileData.get("LastName"));
			Assert.assertEquals(driver.findElement(txtBoxPublicDisplayName).getAttribute("value"),profileData.get("PublicDisplayName"));
			Assert.assertEquals(driver.findElement(txtAreaAboutMe).getAttribute("value"),profileData.get("AboutMe"));
			Assert.assertTrue(driver.findElement(By.xpath("//input[@id='inspector-toggle-control-0']/ancestor::span[1]")).getAttribute("class").equalsIgnoreCase(profileData.get("Gravatar")));
			TestListener2.test.log(LogStatus.PASS, CommonMethods.getCurrentMethodName()+" "+"My Profile page details updated successfully.");
			bFlag=true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName(), e);
		}
		return bFlag;
}
	
}
