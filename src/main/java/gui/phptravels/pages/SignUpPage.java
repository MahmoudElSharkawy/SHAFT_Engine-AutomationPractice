package gui.phptravels.pages;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT;

import io.qameta.allure.Step;

public class SignUpPage {
    private SHAFT.GUI.WebDriver driver;

    // Constructor
    public SignUpPage(SHAFT.GUI.WebDriver driver) {
	this.driver = driver;
    }

    // Element Locators
    private By firstName_textField = By.name("first_name");
    private By lastName_textField = By.name("last_name");
    private By phone_textField = By.name("phone");
    private By email_textField = By.name("email");
    private By password_textField = By.name("password");
    private By signUp_button = By.xpath("//button[contains(.,'Signup')]");
    private By successfull_message = By.xpath("//div[@class='alert alert-success signup']");

    //////////////////////////////////////////////////////
    ////////////////////// Actions //////////////////////
    
    @Step("User Sign Up with Test Data --> First Name: [{firstName}], Last Name: [{lastName}], Phone Number: [{phone}], Email: [{email}], password: [{password}],")
    public void userSignUp(String firstName, String lastName, String phone, String email, String password) {
	driver.element().type(firstName_textField, firstName);
	driver.element().type(lastName_textField, lastName);
	driver.element().type(phone_textField, phone);
	driver.element().type(email_textField, email);
	driver.element().type(password_textField, password);
	driver.element().click(signUp_button);
    }
    
    public By getSuccessfullMessageLocator() {
	return successfull_message;
    }

}
