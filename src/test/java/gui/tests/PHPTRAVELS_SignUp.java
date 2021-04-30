package gui.tests;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.shaft.driver.DriverFactory;
import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import com.shaft.validation.Assertions;
import com.shaft.validation.Assertions.ElementAttributeType;

public class PHPTRAVELS_SignUp {
    Date date = new Date();
    String email = "test" + date.getTime() + "@test.com";

    @Test
    public void userSignUp() {
	WebDriver driver = DriverFactory.getDriver();
	BrowserActions.navigateToURL(driver, "https://www.phptravels.net/home");

	ElementActions.click(driver, By.xpath("//a[contains(.,'My Account')]"));
	ElementActions.click(driver, By.xpath("//a[contains(.,'Sign Up') and contains(@class,'dropdown')]"));
	ElementActions.type(driver, By.name("firstname"), "Mahmoud");
	ElementActions.type(driver, By.name("lastname"), "ElSharkawy");
	ElementActions.type(driver, By.name("phone"), "12345678901");
	ElementActions.type(driver, By.name("email"), email);
	ElementActions.type(driver, By.name("password"), "12345678");
	ElementActions.type(driver, By.name("confirmpassword"), "12345678");
	ElementActions.click(driver, By.xpath("//button[contains(.,' Sign Up')]"));

	Assertions.assertEquals("Hi, Mahmoud ElSharkawy",
		ElementActions.getText(driver, By.xpath("//h3[contains(.,'Hi, ')]")));
	Assertions.assertElementAttribute(driver, By.xpath("//h3[contains(.,'Hi, ')]"), ElementAttributeType.TEXT,
		"Hi, Mahmoud ElSharkawy");
    }
}
