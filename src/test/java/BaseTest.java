import com.sun.org.apache.xpath.internal.operations.Bool;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.BrowserManager;
import utils.Helper;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    protected WebDriver driver;
    private static final String browserStackUserName = Helper.getProperty("BrowserStackUserName");
    private static final String browserStackAutomateKey = Helper.getProperty("BrowserStackAutomateKey");
    private static final String URL = "https://" + browserStackUserName + ":" + browserStackAutomateKey + "@hub-cloud.browserstack.com/wd/hub";
    public static int implicitWaitTime = Integer.parseInt(Helper.getProperty("defaultTimeout"));

    @Parameters({"browser", "browser_version", "os", "os_version", "deviceOrientation"})
    @BeforeClass
    public void setUp(@Optional("Chrome") String browserName,
                      @Optional("129.0") String browser_version,
                      @Optional("OS X") String os,
                      @Optional("Monterey") String os_version,
                      @Optional("portrait") String deviceOrientation,
                      @Optional("iPhone 13") String deviceName,
                      Method method) {
        boolean isBrowserStackExecution= Boolean.parseBoolean(Helper.getProperty("IsBrowserStackExecution"));
        if(isBrowserStackExecution) {
            // Setting driver for execution on browserstack cloud
            if (deviceName != null && !deviceName.isEmpty()) {
                // Mobile Device execution on BrowserStack
                driver = BrowserManager.getBrowserStackMobileDriver(deviceName, browserName, os_version, deviceOrientation, URL);
            } else {
                // Desktop browser execution on BrowserStack
                driver = BrowserManager.getBrowserStackDriver(browserName, os, os_version, browser_version, URL);
            }
        } else {
            // Setting driver for local execution
            driver = BrowserManager.getDriver(browserName);
        }
        driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
