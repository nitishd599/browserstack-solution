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

    @Parameters({"browser", "browser_version", "os", "os_version"})
    @BeforeClass
    public void setUp(String browserName, String browser_version, String os, String os_version, Method name) {
        boolean isBrowserStackExecution= Boolean.parseBoolean(Helper.getProperty("IsBrowserStackExecution"));
        if(isBrowserStackExecution) {
            // Setting driver for execution on browserstack cloud
            driver = BrowserManager.getBrowserStackDriver(browserName, os, os_version, browser_version, URL);
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
