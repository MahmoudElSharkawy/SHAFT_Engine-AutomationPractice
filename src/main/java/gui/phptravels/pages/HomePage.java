package gui.phptravels.pages;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT;

import io.qameta.allure.Step;

public class HomePage {
    private SHAFT.GUI.WebDriver driver;

    // Constructor
    public HomePage(SHAFT.GUI.WebDriver driver) {
	this.driver = driver;
    }

    // Elements Locators
    private By myAccount_button = By.id("ACCOUNT");
    private By signUp_button = By.xpath("//a[contains(.,'Customer Signup') and contains(@class,'dropdown-item')]");

    //////////////////////////////////////////////////////
    ////////////////////// Actions //////////////////////
    
    @Step("Navigate to Sign Up page")
    public void navigateToSignUpPage() {
	driver.element().click(myAccount_button);
	driver.element().click(signUp_button);
    }

}
