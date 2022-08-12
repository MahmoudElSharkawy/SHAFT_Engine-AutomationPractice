package gui.tests;

import java.util.Date;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.shaft.driver.SHAFT;

public class PHPTRAVELS_SignUp {
    String email = "test" + new Date().getTime() + "@test.com";

    @Test
    public void userSignUp() {
	SHAFT.GUI.WebDriver driver = new SHAFT.GUI.WebDriver();
	driver.browser().navigateToURL("https://www.phptravels.net");

	driver.element().click(By.id("ACCOUNT"));
	driver.element().click(By.xpath("//a[contains(.,'Customer Signup') and contains(@class,'dropdown-item')]"));
	driver.element().type(By.name("first_name"), "Mahmoud");
	driver.element().type(By.name("last_name"), "ElSharkawy");
	driver.element().type(By.name("phone"), "12345678901");
	driver.element().type(By.name("email"), email);
	driver.element().type(By.name("password"), "12345678");
	driver.element().click(By.xpath("//button[contains(.,'Signup')]"));

	// Validations
	driver.assertThat().element(By.xpath("//div[@class='alert alert-success signup']")).text()
		.contains("Signup successfull please login.").perform();
    }
}
