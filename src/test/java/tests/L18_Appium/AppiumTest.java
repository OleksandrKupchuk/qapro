package tests.L18_Appium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.time.Duration;
import java.util.*;

import static com.codeborne.selenide.Selenide.$;

public class AppiumTest {
    private AndroidDriver driver;

    @BeforeClass
    public void setUp() {
        BaseOptions options = new BaseOptions()
                .amend("platformName", "android")
                .amend("appium:options", Map.ofEntries(
                        Map.entry("automationName", "uiautomator2"),
                        Map.entry("platformVersion", "14"),
                        Map.entry("deviceName", "emulator-5554"),
//                        Map.entry("app", "C:\\application\\SauceLabs.apk"),
                        Map.entry("app", "C:\\application\\AdvancedRecyclerViewExamples.apk"),
//                        Map.entry("appPackage", "com.swaglabsmobileapp"),
                        Map.entry("appPackage", "com.h6ah4i.android.example.advrecyclerview"),
                        Map.entry("appActivity", "com.h6ah4i.android.example.advrecyclerview.launcher.MainActivity")))
//                        Map.entry("appActivity", "com.swaglabsmobileapp.MainActivity")))

                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebDriverRunner.setWebDriver(driver);
    }


    @Test
    public void loginTest() {
        $(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
//        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
//        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        $(AppiumBy.accessibilityId("test-Password")).shouldBe(Condition.visible).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\"PRODUCTS\"]")).isDisplayed();
    }

    @Test
    public void scrollTest() {
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        scroll();
        Selenide.sleep(2000);
    }

    private void scroll(){
        Dimension size = driver.manage().window().getSize();

        int startX = size.width / 2;
        int endX = startX;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragNDrop = new Sequence(finger, 1);
        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(500),
                PointerInput.Origin.viewport(), startX, startY));
        dragNDrop.addAction(finger.createPointerDown(PointerInput
                .MouseButton.LEFT.asArg()));
        dragNDrop.addAction(finger.createPointerMove(Duration.ofSeconds(1),
                PointerInput.Origin.viewport(), endX, endY));
        dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(dragNDrop));
    }

    @Test
    public void switchContext(){
        driver.findElement(AppiumBy.accessibilityId("test-Username")).sendKeys("standard_user");
        driver.findElement(AppiumBy.accessibilityId("test-Password")).sendKeys("secret_sauce");
        driver.findElement(AppiumBy.accessibilityId("test-LOGIN")).click();
        driver.findElement(AppiumBy.accessibilityId("test-Menu")).click();
        driver.findElement(AppiumBy.accessibilityId("test-WEBVIEW")).click();
        driver.findElement(AppiumBy.accessibilityId("test-enter a https url here...")).sendKeys("https://www.google.com");
        driver.findElement(AppiumBy.accessibilityId("test-GO TO SITE")).click();

        System.out.println("current context = " + driver.getContext());
        System.out.println("current handles = " + driver.getContextHandles());
        try{
            Selenide.Wait().until(webDriver -> (driver.getContextHandles().size() > 1));
            Set<String> contextName = driver.getContextHandles();
            List<String> handles = new ArrayList<>(contextName);
            String webViewContext = handles.get(1);
            driver.context(webViewContext);
        }catch (TimeoutException exception){
            throw new TimeoutException("no context");
        }
        System.out.println("current context = " + driver.getContext());
        System.out.println("current handles = " + driver.getContextHandles());
        driver.findElement(By.xpath("//*[@name='q']")).sendKeys("appium");
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER);
        action.perform();
        Selenide.sleep(3000);
    }

    @Test
    public void dragAndDropTest() {
        driver.findElement(AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.h6ah4i.android.example.advrecyclerview:id/button\" and @text=\"DRAGGABLE (MINIMAL)\"]")).click();
        WebElement fromElement = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Item 0\"]"));
        WebElement toElement = driver.findElement(AppiumBy.xpath("//android.widget.TextView[@resource-id=\"android:id/text1\" and @text=\"Item 8\"]"));
//        dragAndDrop(fromElement, toElement);
        System.out.println(driver.getPageSource());
        driver.findElement(AppiumBy.id("com.h6ah4i.android.example.advrecyclerview:id/snackbar_text")).isDisplayed();
        Selenide.sleep(1000);
    }

    private void dragAndDrop(WebElement fromElement, WebElement toElement){
        Point fromPoint = fromElement.getLocation();
        Point toPoint = toElement.getLocation();

        Dimension fromSize = fromElement.getSize();
        Dimension toSize = toElement.getSize();

        int startX = fromPoint.getX() + (fromSize.getWidth() / 2);
        int startY = fromPoint.getY() + (fromSize.getHeight() / 2);
        int endX = toPoint.getX() + (toSize.getWidth() / 2);
        int endY = toPoint.getY() + (toSize.getHeight() / 2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence dragNDrop = new Sequence(finger, 1);
        dragNDrop.addAction(finger.createPointerMove(Duration.ofMillis(500),
                PointerInput.Origin.viewport(), startX, startY));
        dragNDrop.addAction(finger.createPointerDown(PointerInput
                .MouseButton.LEFT.asArg()));
        dragNDrop.addAction(new Pause(finger, Duration.ofSeconds(2)));
        dragNDrop.addAction(finger.createPointerMove(Duration.ofSeconds(1),
                PointerInput.Origin.viewport(), endX, endY));
        dragNDrop.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(dragNDrop));
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
