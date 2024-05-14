package tests.L19_Playwright_Route_And_Browserstack;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class Browserstack {
    private AndroidDriver driver;

    @BeforeClass
    public void setUp() {
        String userName = "your_userName";
        String accessKey = "your_accessKey";

        DesiredCapabilities options = new DesiredCapabilities();
        options.setCapability("platformName", "android");
        options.setCapability("deviceName", "Google Pixel 6");
        options.setCapability("os_version", "12.0");
        options.setCapability("app", "bs://3b8f70670f3ba47d298a43d90a4f9f05146a7b5f");
        options.setCapability("browserstack.local", false);
        options.setCapability("build", "AQA test");
        options.setCapability("project", "Some pr");
        options.setCapability("interactiveDebugging", true);
        try {
            driver = new AndroidDriver(new URL("https://" + userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub"), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WebDriverRunner.setWebDriver(driver);
    }

    //need install Browserstack plugin
//    @BeforeClass
//    public void setUp() {
//        String userName = "bsuser_Rqhw3H";
//        String accessKey = "33vX6gqrZuuGuUi2Yesx";
//        DesiredCapabilities options = new DesiredCapabilities();
//        driver = new AndroidDriver(options);
//        WebDriverRunner.setWebDriver(driver);
//    }

    @Test
    public void dragAndDropTest() {
        driver.findElement(AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.h6ah4i.android.example.advrecyclerview:id/button\" and @text=\"DRAGGABLE (MINIMAL)\"]")).click();
        System.out.println(driver.getPageSource());
        driver.findElement(AppiumBy.id("com.h6ah4i.android.example.advrecyclerview:id/snackbar_text")).isDisplayed();
        Selenide.sleep(6000);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
