package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BrowserManager {
    public static WebDriver getDriver(String browser) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        return driver;
    }

    public static WebDriver getBrowserStackDriver(String browserName, String os, String os_version, String browser_version, String remoteURL) {
        WebDriver driver = null;
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("os", os);
        caps.setCapability("os_version", os_version);
        caps.setCapability("browser_version", browser_version);

        // Set browser-specific capabilities
        if (browserName.equalsIgnoreCase("Chrome")) {
            caps.setCapability("browser", "Chrome");
        } else if (browserName.equalsIgnoreCase("Firefox")) {
            caps.setCapability("browser", "Firefox");
        } else if (browserName.equalsIgnoreCase("Edge")) {
            caps.setCapability("browser", "Edge");
        } else {
            throw new IllegalArgumentException("Unsupported BrowserStack browser: " + browserName);
        }

        try {
            driver = new RemoteWebDriver(new URL(remoteURL), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }
}
