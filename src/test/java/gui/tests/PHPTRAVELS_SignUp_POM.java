package gui.tests;

import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.driver.SHAFT;

import gui.phptravels.pages.HomePage;
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
    private SHAFT.GUI.WebDriver driver;

    private HomePage homePage;
    private SignUpPage signUpPage;

    String email = "test" + new Date().getTime() + "@test.com";

    @BeforeClass
    public void beforeClass() {
	driver = new SHAFT.GUI.WebDriver();
	driver.browser().navigateToURL("https://www.phptravels.net");

	homePage = new HomePage(driver);
	signUpPage = new SignUpPage(driver);
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

	// Validations
	driver.assertThat().element(signUpPage.getSuccessfullMessageLocator()).text()
		.contains("Signup successfull please login.").perform();
    }
}
