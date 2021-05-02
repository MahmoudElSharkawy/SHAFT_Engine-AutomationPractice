package gui.phptravels.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.element.ElementActions;

import io.qameta.allure.Step;

public class HomePage {
    private WebDriver driver;

    // Constructor
    public HomePage(WebDriver driver) {
	this.driver = driver;
    }

    // Elements Locators
    private By myAccount_button = By.xpath("//a[contains(.,'My Account')]");
    private By signUp_button = By.xpath("//a[contains(.,'Sign Up') and contains(@class,'dropdown')]");

    //////////////////////////////////////////////////////
    ////////////////////// Actions //////////////////////
    
    @Step("Navigate to Sign Up page")
    public void navigateToSignUpPage() {
	ElementActions.click(driver, myAccount_button);
	ElementActions.click(driver, signUp_button);
    }

}
