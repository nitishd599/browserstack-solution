package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OpinionPage {
    WebDriver driver;

    public OpinionPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getOpinionHeaderOnTab() {
        By by = By.xpath("//div[@id='ctn_head']/following-sibling::div//nav//a[contains(@href,'opinion')]");
        return driver.findElement(by);
    }

    public List<WebElement> getTotalArticles() {
        By by = By.xpath("//main//article");
        return driver.findElements(by);
    }
    public WebElement getArticleDetails(int index, String detail) {
        String xPath = "";
        if(detail.equalsIgnoreCase("header"))
            xPath="(//main//article)["+ index +"]//h2/a";
        else if(detail.equalsIgnoreCase("content"))
            xPath="(//main//article)["+ index +"]//p";
        By by = By.xpath(xPath);
        return driver.findElement(by);
    }

    public WebElement getArticleCoverImage(int index) {
        String xpath = "(//main//article)["+ index +"]/figure";
        // Using findElements() to avoid exception when image is not found
        List<WebElement> images = driver.findElements(By.xpath(xpath));
        // If an image is found, return the first one, otherwise return null
        if (images.isEmpty()) {
            // No image found
            System.out.println("No cover image found for Article " + index);
            return null;
        } else {
            // Return the first image element found
            return images.get(0);
        }
    }

}
