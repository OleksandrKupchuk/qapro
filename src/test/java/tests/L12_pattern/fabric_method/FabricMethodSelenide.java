package tests.L12_pattern.fabric_method;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FabricMethodSelenide {
    private static WebDriver driver;
    public static WebDriver setBrowser(BrowserType browserType){
        if (browserType.equals(BrowserType.CHROME)){
            driver = new ChromeDriver();
            WebDriverRunner.setWebDriver(driver);
        }
        if (browserType.equals(BrowserType.FIREFOX)){
            driver = new FirefoxDriver();
            WebDriverRunner.setWebDriver(driver);
        }

        return driver;
    }
}
