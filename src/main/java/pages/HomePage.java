package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getButtonsOnTopNotification(String buttonText) {
        String text="";
        if(buttonText.equalsIgnoreCase("Agree"))
            text="agree-button";
        else if(buttonText.equalsIgnoreCase("learn more"))
            text="learn-more-button";
        By by = By.xpath("//div[@id='buttons']/button[contains(@id,'"+ text +"')]");
        return driver.findElement(by);
    }

}