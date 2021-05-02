package gui.tests;

import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.driver.DriverFactory;
import com.shaft.gui.browser.BrowserActions;
import com.shaft.validation.Assertions;
import com.shaft.validation.Assertions.ElementAttributeType;

import gui.phptravels.pages.HomePage;
import gui.phptravels.pages.ProfilePage;
import gui.phptravels.pages.SignUpPage;

public class PHPTRAVELS_SignUp_POM {
    private WebDriver driver;

    private HomePage homePage;
    private SignUpPage signUpPage;
    private ProfilePage profilePage;
    
    Date date = new Date();
    String email = "test" + date.getTime() + "@test.com";

    @BeforeClass
    public void beforeClass() {
	driver = DriverFactory.getDriver();
	BrowserActions.navigateToURL(driver, "https://www.phptravels.net/home");

	homePage = new HomePage(driver);
	signUpPage = new SignUpPage(driver);
	profilePage = new ProfilePage(driver);
    }

    @Test(description = "Validate The Sign Up feature")
    public void signUp() {
	homePage.navigateToSignUpPage();
	signUpPage.userSignUp("Mahmoud", "ElSharkawy", "12345678909", email, "12345678");

	Assertions.assertEquals("Hi, Mahmoud ElSharkawy", profilePage.getHiMessageText());
	Assertions.assertElementAttribute(driver, profilePage.getHiMessageLocator(), ElementAttributeType.TEXT,
		"Hi, Mahmoud ElSharkawy");
    }
}
