package gui.phptravels.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.element.ElementActions;

import io.qameta.allure.Step;

public class SignUpPage {
    private WebDriver driver;

    // Constructor
    public SignUpPage(WebDriver driver) {
	this.driver = driver;
    }

    // Element Locators
    private By firstName_textField = By.name("firstname");
    private By lastName_textField = By.name("lastname");
    private By phone_textField = By.name("phone");
    private By email_textField = By.name("email");
    private By password_textField = By.name("password");
    private By confirmPassword_textField = By.name("confirmpassword");
    private By signUp_button = By.xpath("//button[contains(.,' Sign Up')]");

    // Actions
    @Step("User Sign Up with Test Data --> First Name: [{firstName}], Last Name: [{lastName}], Phone Number: [{phone}], Email: [{email}], password: [{password}],")
    public void userSignUp(String firstName, String lastName, String phone, String email, String password) {
	new ElementActions(driver)
	.type(firstName_textField, firstName)
	.type(lastName_textField, lastName)
	.type(phone_textField, phone)
	.type(email_textField, email)
	.type(password_textField, password)
	.type(confirmPassword_textField, password)
	.click(signUp_button);
    }

}
