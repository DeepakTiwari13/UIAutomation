package com.assignment.ui.testcase;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.assignment.ui.base.baseTest;

public class newuserScreen extends baseTest {
	SoftAssert softAssert = new SoftAssert();

	@BeforeTest
	private void initializeConfigData() {
		initFile();
	}

	@Test(priority=1)
	private void verifyNewUserScreen() {
		openBrowser("browser");
		navigate("url");
		click("Userslink_xpath");
		click("Newuserbutton_xpath");
		softAssert.assertTrue(iselementexist("Usernamelabel_xpath"));
		softAssert.assertTrue(iselementexist("Passwordlabel_xpath"));
		softAssert.assertTrue(iselementexist("Emaillabel_xpath"));
		softAssert.assertTrue(iselementexist("Createuserbutton_xpath"));
	}
	@Test(priority=2)
	private void verifyUsersFilters() {
	//	openBrowser("browser");
		navigate("url");
		click("Userslink_xpath");
		getelement("Usernamefilter_xpath").sendKeys("Contains");
		type("Usernameinputbox_xpath","Test");
		getelement("Emailfilter_xpath").sendKeys("Contains");
		type("Emailinputbox_xpath","yopmail.com");
		click("Fromdatepicker_xpath");
		type("Fromdatepicker_xpath","2018-11-21");
		click("Todatepicker_xpath");
		type("Todatepicker_xpath","2018-11-21");
		click("Filterbutton_xpath");
		Assert.assertNotNull(getelement("Rowone_xpath"));
	}

	@AfterTest()
		private void closeBrowser() {
		Close();
		softAssert.assertAll();
	}
	
}
