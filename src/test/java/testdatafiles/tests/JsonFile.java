package testdatafiles.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.shaft.driver.DriverFactory;
import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.JSONFileManager;

public class JsonFile {
    private WebDriver driver;
    JSONFileManager jsonFileTestDataReader = new JSONFileManager(
	    System.getProperty("testDataFolderPath") + "JsonFile.json");

    @Test
    public void jsonFile() {
	driver = DriverFactory.getDriver();
	BrowserActions.navigateToURL(driver, "https://www.google.com/");

	ElementActions.type(driver, By.name("q"), jsonFileTestDataReader.getTestData("searchData"));
	ElementActions.keyPress(driver, By.name("q"), Keys.ENTER);
    }
}
