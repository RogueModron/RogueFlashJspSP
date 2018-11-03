package app.selenium;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSelenium
{
	private final String appAddress = "http://localhost:8080/RogueFlashJspSP";
	
	
	private static WebDriver getWebDriver() throws MalformedURLException
	{
		// See:
		//	https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver
		//	http://chromedriver.chromium.org/getting-started
		
		return new RemoteWebDriver(
				new URL("http://localhost:9515"),
				DesiredCapabilities.chrome());
	}
	
	
	private WebDriver driver = null;
	
	
	private void go(String relativeUrl)
	{
		driver.get(appAddress + relativeUrl);
	}
	
	private void wait(ExpectedCondition<?> expectedCondition)
	{
		WebDriverWait driverWait = new WebDriverWait(driver, 5);
		driverWait.until(expectedCondition);
	}
	
	private static void waitMillis(int millis) throws InterruptedException
	{
		// FIXME:
		// http://stackoverflow.com/questions/6201425/wait-for-an-ajax-call-to-complete-with-selenium-2-web-driver
		
		Thread.sleep(millis);
	}
	
	private static void waitMillis() throws InterruptedException
	{
		waitMillis(200);
	}
	
	
	@BeforeEach
	public void beforeEach() throws MalformedURLException
	{
		driver = getWebDriver();
	}
	
	@AfterEach
	public void t()
	{
		driver.quit();
	}
	
	
	@Test
	public void test() throws Exception
	{
		go("/decks");
		
		wait(ExpectedConditions.elementToBeClickable(By.id("openAppMenu")));
		WebElement openMenuButtonElement = driver.findElement(By.id("openAppMenu"));
		openMenuButtonElement.click();
		
		wait(ExpectedConditions.elementToBeClickable(By.id("newDeck")));
		WebElement buttonElement = driver.findElement(By.id("newDeck"));
		buttonElement.click();
		
		wait(ExpectedConditions.elementToBeClickable(By.id("description")));
		WebElement descriptionElement = driver.findElement(By.id("description"));
		descriptionElement.click();
		descriptionElement.sendKeys("WebDriver Test!!!");
		
		wait(ExpectedConditions.elementToBeClickable(By.id("openAppMenu")));
		openMenuButtonElement = driver.findElement(By.id("openAppMenu"));
		openMenuButtonElement.click();
		
		waitMillis();
		
		buttonElement = driver.findElement(By.id("decks"));
		buttonElement.click();
		
		waitMillis();
		
		wait(ExpectedConditions.elementToBeClickable(By.id("filterText")));
		WebElement filterTextElement = driver.findElement(By.id("filterText"));
		filterTextElement.click();
		filterTextElement.sendKeys("WebDriver Test!!!");
		
		buttonElement = driver.findElement(By.id("executeFilter"));
		buttonElement.click();
		
		waitMillis();
		
		List<WebElement> itemElements = driver.findElements(By.className("itemId"));
		
		assertTrue(itemElements.size() > 0);
	}
}
