
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.OpinionPage;
import utils.Helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpanishArticleTest extends BaseTest{

    @Test
    public void spanishArticleVerificationTest() {
        // navigate to application
        String applicationURL=Helper.getProperty("AppURL");
        driver.get(applicationURL);
        // click agree on displayed notification
        HomePage homePage=new HomePage(driver);
        Helper.clickOnElement(homePage.getButtonsOnTopNotification("Agree"), "Agree button");
        // click on opinion header
        OpinionPage opinionPage=new OpinionPage(driver);
        Helper.clickOnElement(opinionPage.getOpinionHeaderOnTab(), "Opinion header");
        String getCurrentURL=driver.getCurrentUrl();
        // verify user is redirected to opinions page after click action
        Assert.assertEquals(getCurrentURL, applicationURL+"opinion/");
        // Get list of articles on opinions page
        List<WebElement> listOfArticles=opinionPage.getTotalArticles();
        // Verify total number of articles present on opinions page is greater than 5
        Assert.assertEquals(listOfArticles.size()>=5, true);
        // A map to store the occurrences of words across all article headers
        Map<String, Integer> wordCountMap = new HashMap<>();
        // print title & content of first 5 articles and download cover image if available
        for(int i=1; i<=5; i++) {
            String articleHeader=Helper.getElementText(opinionPage.getArticleDetails(i, "header"));
            String articleContent=Helper.getElementText(opinionPage.getArticleDetails(i, "content"));
            System.out.println("Article "+ i +" header : "+articleHeader);
            System.out.println("Article "+ i +" content : "+articleContent);

            // Downloading the cover images if present
            WebElement coverImageElement = opinionPage.getArticleCoverImage(i);
            Helper.downloadImage(coverImageElement, i);

            // Translating the article headers to English
            String translatedHeader = Helper.translateText("es", "en", articleHeader);
            // Adding words from the translated header to the word count map
            if (translatedHeader != null) {
                String[] words = translatedHeader.split("\\s+");
                for (String word : words) {
                    word = word.toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }
            System.out.println("Translated header text for Article " + i + ": " + translatedHeader);
        }

        // After processing all the headers, analyzing the repeated words
        System.out.println("\nRepeated words across all headers (appearing more than twice):");
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            if (entry.getValue() > 2) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}
