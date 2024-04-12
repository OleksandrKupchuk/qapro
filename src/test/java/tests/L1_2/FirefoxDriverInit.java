package tests.L1_2;

import com.codeborne.selenide.WebDriverProvider;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import javax.annotation.Nonnull;

public class FirefoxDriverInit implements WebDriverProvider {
    @Nonnull
    @Override
    public WebDriver createDriver(@Nonnull Capabilities capabilities) {
        FirefoxOptions options = new FirefoxOptions();
//        options.setCapability("browserVersion", "122.0");
        FirefoxDriver driver = new FirefoxDriver(options);
//        driver.manage().window().maximize();
        return driver;
    }
}
