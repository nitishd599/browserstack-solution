package utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.net.URL;
import java.util.Properties;

public class Helper {

    public static String getProperty(String key) {
        Properties properties = new Properties();
        try {
            InputStream input = new FileInputStream("src/main/resources/config.properties");
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return properties.getProperty(key);
    }

    public static void clickOnElement(WebElement element, String detail) {
        try {
            element.click();
            System.out.println("Performed click on element : "+detail);
        } catch (Exception e) {
            System.out.println("Unable to perform click on element : "+detail);
        }
    }

    public static String getElementText(WebElement element) {
        return element.getText();
    }

    public static void downloadImage(String imageUrl, String fileName) throws IOException {
        URL url = new URL(imageUrl);
        InputStream in = url.openStream();
        FileOutputStream fos = new FileOutputStream(new File("downloaded_images/" + fileName));
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) != -1) {
            fos.write(buffer, 0, length);
        }
        fos.close();
        in.close();
        System.out.println("Downloaded image: " + fileName);
    }

    public static void downloadImage(WebElement coverImageElement, int index) {
        if (coverImageElement != null) {
            String imageUrl = coverImageElement.getAttribute("href");
            if (imageUrl != null && !imageUrl.isEmpty()) {
                try {
                    downloadImage(imageUrl, "cover_image_" + index + ".jpg");
                } catch (IOException e) {
                    System.out.println("Error downloading image for article " + index + ": " + e.getMessage());
                }
            }
        }
    }

    public static String translateText(String fromLanguage, String toLanguage, String textToTranslate) {
        String url = getProperty("TranslationApiBaseURL");
        String api_key = getProperty("API_KEY");
        String api_host = getProperty("API_HOST");
        String jsonBody = "{\n" +
                "    \"from\": \"" + fromLanguage + "\",\n" +
                "    \"to\": \"" + toLanguage + "\",\n" +
                "    \"q\": \"" + textToTranslate + "\"\n" +
                "}";

        Response response = RestAssured.given()
                .header("x-rapidapi-key", api_key)
                .header("x-rapidapi-host", api_host)
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .post(url);

        if (response.getStatusCode() == 200) {
            String translatedText = response.jsonPath().getString("[0]");
            return translatedText;
        } else {
            System.out.println("Translation failed. Status Code: " + response.getStatusCode());
            return null;
        }
    }

}
