package tests.L11_playwright_part4;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDataProvider implements WebDriverProvider {
    @NotNull
    @Override
    public WebDriver createDriver(@NotNull Capabilities capabilities) {
        Configuration.timeout = 30000;
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        options.merge(capabilities);
        return new ChromeDriver(options);
    }
}
