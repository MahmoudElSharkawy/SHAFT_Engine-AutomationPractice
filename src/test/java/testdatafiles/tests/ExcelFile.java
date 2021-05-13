package testdatafiles.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.driver.DriverFactory;
import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ExcelFileManager;

public class ExcelFile {
    private WebDriver driver;
    private ExcelFileManager excelFileTestDataReader;

    @BeforeClass
    public void beforeClass() {
	excelFileTestDataReader = new ExcelFileManager(System.getProperty("testDataFolderPath") + "ExcelFile.xlsx");
    }

    @Test
    public void excelFile() {
	driver = DriverFactory.getDriver();
	BrowserActions.navigateToURL(driver, "https://www.google.com/");

	ElementActions.type(driver, By.name("q"), excelFileTestDataReader.getCellData("searchData"));
	ElementActions.keyPress(driver, By.name("q"), Keys.ENTER);
    }
}
