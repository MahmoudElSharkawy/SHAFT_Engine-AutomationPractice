package gui.phptravels.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.gui.element.ElementActions;

public class ProfilePage {
    private WebDriver driver;

    // Constructor
    public ProfilePage(WebDriver driver) {
	this.driver = driver;
    }

    // Element Locators
    private By hiMessage_text = By.xpath("//h3[contains(.,'Hi, ')]");

    // Actions
    public String getHiMessageText() {
	return ElementActions.getText(driver, hiMessage_text);
    }
    
    public By getHiMessageLocator() {
	return hiMessage_text;
    }
}
