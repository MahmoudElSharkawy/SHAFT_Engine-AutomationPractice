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
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;

@Epic("Web GUI")
@Feature("PHPTRAVELS")
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
    @Description("When I enter valid data in the sign up form And click the signup button, Then I should be signed up successfully And should be navigated to the user account page And I should see my user data and a Hi message with my first name and last name")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Sign Up")
    @TmsLink("Test_case")
    @Issue("Software_bug")
    public void signUp() {
	homePage.navigateToSignUpPage();
	signUpPage.userSignUp("Mahmoud", "ElSharkawy", "12345678909", email, "12345678");

	Assertions.assertEquals("Hi, Mahmoud ElSharkawy", profilePage.getHiMessageText());
	Assertions.assertElementAttribute(driver, profilePage.getHiMessageLocator(), ElementAttributeType.TEXT,
		"Hi, Mahmoud ElSharkawy");
    }
}
