package com.wordpress.pageobjects;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.codoid.products.fillo.Select;
import com.commonmethods.CommonMethods;
import com.listeners.TestListener2;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.BaseDriver;
import com.utilities.PropHandler;

public class LoginPage extends BaseDriver {

		public WebDriver driver;
		PropHandler prop;

		/*
		 * Login Page Objects.
		 */
		private By txtBoxUserName = By.id("usernameOrEmail");
		private By txtBoxPassword = By.id("password");
		private By btnLoginOrContinue = By.xpath("//button[@type='submit' and text()='Log In' or text()='Continue']");
		private By headerMyProfilePage = By.xpath("//h1[contains(text(),'My') and contains(text(),'Profile')]");
		private By btnLogout = By.xpath("//button[text()='Log out']"); 
		private By btnLogIn = By.xpath("//a[text()='Log In']"); 
		
		public LoginPage(WebDriver driver) {
			this.driver = driver;
			prop=new PropHandler();
		}

		/**
		 * Method for login by entering valid credentials.
		 * @return
		 */
		public boolean login() {
			boolean bFlag = false;
			try {
				driver.get(prop.getPropertyValue("URL"));
				new WebDriverWait(driver, 20).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxUserName));
				System.out.println(driver.getTitle());
				if (driver.getTitle().equalsIgnoreCase("Log In — WordPress.com")) {
					new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxUserName));
					driver.findElement(txtBoxUserName).clear();
					driver.findElement((txtBoxUserName)).sendKeys(prop.getPropertyValue("UserName"));
					driver.findElement(btnLoginOrContinue).click();
					new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(txtBoxPassword));
					driver.findElement(txtBoxPassword).clear();
					driver.findElement(txtBoxPassword).sendKeys(prop.getPropertyValue("Password"));
					new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(btnLoginOrContinue));
					driver.findElement(btnLoginOrContinue).click();
					new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(headerMyProfilePage));
					System.out.println(driver.getTitle());
					if(driver.getTitle().contains("Profile"))
					{
						TestListener2.test.log(LogStatus.PASS, CommonMethods.getCurrentMethodName()+" "+"Login test passed successfully");
						bFlag=true;
					}
					else {
						TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName()+" "+"My Profile page has not been displayed.");
						System.out.println("My Profile page has not been displayed.");
					}

				} else {
					TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName()+" "+"User is not re-directing to correct page");
					System.out.println("User is not re-directing to correct page");
				}
			} catch (Exception e) {
				e.printStackTrace();
				TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName(), e);
			}
			return bFlag;
		}

		/**
		 * Method for logout.
		 * @return bFlag
		 */
		public boolean logout() {
			boolean bFlag = false;
			try {
				new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(btnLogout));
				if (CommonMethods.IsElementDisplayed(driver, btnLogout)) {
					driver.findElement(btnLogout).click();
					Thread.sleep(1000);
					if(CommonMethods.isAlertPresent(driver))
					{
						driver.switchTo().alert().accept();
					}
					new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(btnLogIn));
					if (CommonMethods.IsElementDisplayed(driver, btnLogIn)&&(driver.getTitle().equalsIgnoreCase("WordPress.com"))) {
						TestListener2.test.log(LogStatus.PASS, CommonMethods.getCurrentMethodName()+" "+"User has been logged out successfully");
						bFlag = true;
					} else {
						System.out.println("Login link after logout is not getting displayed.");
						TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName()+" "+"Login link after logout is not getting displayed.");
					}
				} else {
					System.out.println("Logout button is not getting displayed.");
					TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName()+" "+"Logout button is not getting displayed.");
				}
			} catch (Exception e) {
				e.printStackTrace();
				TestListener2.test.log(LogStatus.FAIL, CommonMethods.getCurrentMethodName(), e);
			}
			return bFlag;
		}
	}


