package gui.tests;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.shaft.driver.DriverFactory;
import com.shaft.gui.browser.BrowserActions;
import com.shaft.gui.element.ElementActions;
import com.shaft.validation.Validations;
import com.shaft.validation.ValidationEnums.ElementAttribute;

public class PHPTRAVELS_SignUp {
    String email = "test" + new Date().getTime() + "@test.com";

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

	// Validations
	Validations.assertThat().object(ElementActions.getText(driver, By.xpath("//h3[contains(.,'Hi, ')]")))
		.isEqualTo("Hi, Mahmoud ElSharkawy").perform();
	Validations.assertThat().element(driver, By.xpath("//h3[contains(.,'Hi, ')]")).attribute(ElementAttribute.TEXT)
		.isEqualTo("Hi, Mahmoud ElSharkawy").perform();
    }
}
