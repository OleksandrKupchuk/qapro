package tests.L1_2;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.annotation.Nonnull;

public class ChromeDriverInit implements WebDriverProvider {
    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--start-maximized");
        chromeOptions.merge(capabilities);
        ChromeDriver driver = new ChromeDriver(chromeOptions);
//        chromeOptions.addArguments("--headless=new");
        driver.manage().window().maximize();
        return  driver;
    }
}
