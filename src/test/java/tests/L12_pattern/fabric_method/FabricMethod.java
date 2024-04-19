package tests.L12_pattern.fabric_method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FabricMethod {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver getDriver(BrowserType browserType){
        if (browserType.equals(BrowserType.CHROME)){
            driver.set(new ChromeDriver());
        }
        if (browserType.equals(BrowserType.FIREFOX)){
            driver.set(new FirefoxDriver());
        }

        return driver.get();
    }

    public static void close(){
        driver.get().close();
    }
}
