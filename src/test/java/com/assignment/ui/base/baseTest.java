package com.assignment.ui.base;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class baseTest {

	Properties CON;
	Properties OR;
	WebDriver driver;
	String parentWindowHandler = null;
	String subWindowHandler = null;
	FileInputStream Fis = null;

	protected void initFile() {
		if (CON == null) {
			try {

				CON = new Properties();
				Fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\project.properties");
				CON.load(Fis);

				OR = new Properties();
				Fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\or.properties");
				OR.load(Fis);

			} catch (Throwable t) {
				System.out.println("Error occurred while loading property files " + t.fillInStackTrace());
			}
		}
	}

	protected void openBrowser(String browserType) {

		String btype = CON.getProperty(browserType);

		if (btype.equals("Mozilla")) {

			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "\\src\\test\\java\\drivers\\geckodriver.exe");

			driver = new FirefoxDriver();

		} else if (btype.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\test\\java\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (btype.equals("IE")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\src\\test\\java\\drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else {
			System.out.println("Failed to match driver configuration ");
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	protected void navigate(String testurl) {
		driver.get(CON.getProperty(testurl));
	}

	protected boolean iselementexist(String Elementkey) {

		List<WebElement> Listid = null;

		if (Elementkey.endsWith("_id")) {
			Listid = driver.findElements(By.id(OR.getProperty(Elementkey)));
		} else if (Elementkey.endsWith("_xpath")) {
			Listid = driver.findElements(By.xpath(OR.getProperty(Elementkey)));
		} else if (Elementkey.endsWith("_name")) {
			Listid = driver.findElements(By.name(OR.getProperty(Elementkey)));
		} else {
			Assert.fail("Locator not correct [Is Element Exist] - " + OR.getProperty(Elementkey));
		}
		if (Listid.size() == 0) {
			return false;
		} else
			return true;
	}

	public WebElement getelement(String ElementKey) {

		WebElement e = null;
		WebDriverWait wait = null;

		try {
			if (ElementKey.endsWith("_id")) {
				wait = new WebDriverWait(driver, 30);
				e = wait.until(ExpectedConditions.elementToBeClickable(By.id(OR.getProperty(ElementKey))));
			} else if (ElementKey.endsWith("_xpath")) {
				wait = new WebDriverWait(driver, 30);
				e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(OR.getProperty(ElementKey))));
			} else if (ElementKey.endsWith("_name")) {
				wait = new WebDriverWait(driver, 30);
				e = wait.until(ExpectedConditions.elementToBeClickable(By.name(OR.getProperty(ElementKey))));
			} else {
				Assert.fail("Locator not correct [Get Element ] - " + OR.getProperty(ElementKey));
			}
		} catch (Exception er) {
			ReportFailure("Failed to get element [getelement] " + OR.getProperty(ElementKey));
		}
		return e;
	}

	protected void type(String ElementKey, String text) {
		getelement(ElementKey).sendKeys(text);
	}

	protected void click(String ElementKey) {
		getelement(ElementKey).click();
	}

	private WebElement waitforspecificelement(String ElementKey) {
		WebElement e1 = null;

		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			e1 = wait.until(ExpectedConditions.elementToBeClickable(getelement(ElementKey)));
		} catch (Exception e) {
			ReportFailure("Error in locating element [waitforspecificelement] ");
		}
		return e1;
	}

	private void handletopopup() {

		parentWindowHandler = driver.getWindowHandle(); // Store your parent window

		Set<String> handles = driver.getWindowHandles(); // get all window handles
		Iterator<String> iterator = handles.iterator();
		while (iterator.hasNext()) {
			subWindowHandler = iterator.next();
			driver.switchTo().window(subWindowHandler);
			if (!subWindowHandler.equals(parentWindowHandler)) {
				Close();
			} else {
				Switchtodefault();
			}
		}

	}

	private void Switchtodefault() {
		driver.switchTo().window(parentWindowHandler);
	}

	private void getfontproperty(String ElementKey) {
		getfontfamily(ElementKey);
		getfontsize(ElementKey);
		getfontcolor(ElementKey);

	}

	private void getfontsize(String ElementKey) {
		String FontSize = getelement(ElementKey).getCssValue("font-size");
	}

	private void getfontcolor(String ElementKey) {
		String FontColor = getelement(ElementKey).getCssValue("color");
	}

	private void getfontfamily(String ElementKey) {
		String FontFamily = getelement(ElementKey).getCssValue("font-family");
	}

	public void ReportPass(String msg) {

	}

	public void ReportFailure(String msg) {
		Assert.fail(msg);
	}

	protected void Close() {
		if(!(driver==null)) {
		driver.close();
		}
	}

	private void Quit() {
		if(!(driver==null)) {
			driver.quit();
		}
	}

	private WebDriver getDriver() {
		return driver;
	}
	
}
