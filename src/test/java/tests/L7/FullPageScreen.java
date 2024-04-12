package tests.L7;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v121.page.Page;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.*;


public class FullPageScreen {

    @Test
    public void testFierefox() throws IOException {
        Configuration.browser = "firefox";
        open("http://www.jtricks.com/bits/content_disposition");
        File fullPageScreenshot = ((FirefoxDriver) webdriver().object()).getFullPageScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(fullPageScreenshot, new File("f1.png"));
        Selenide.screenshot("selenide.png");

    }

    @Test
    public void testChrome() throws IOException {
        Configuration.browser = "chrome";
        open("http://www.jtricks.com/bits/content_disposition");
        DevTools devTools = ((HasDevTools)webdriver().object()).maybeGetDevTools().get();
        devTools.createSession();
        devTools.send(org.openqa.selenium.devtools.v122.page.Page.enable());
        String png = devTools.send(org.openqa.selenium.devtools.v121.page.Page.captureScreenshot(
                Optional.of(Page.CaptureScreenshotFormat.PNG),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(true),
                Optional.empty()
        ));
        FileUtils.writeByteArrayToFile(new File("chromeShot.png"),
                Base64.getDecoder().decode(png));
    }
}
