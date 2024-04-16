package tests.L11_playwright_part4;

import com.automation.remarks.testng.VideoListener;
import com.automation.remarks.video.annotations.Video;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Listeners(VideoListener.class)
public class Tests {

    @Test
    @Video(name = "screenshotFullPageTest")
    public void screenshotFullPageTest() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(1000));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://playwright.dev/java/");
        page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get("playwright.png")));
    }

    @BeforeMethod
    public void beforeMethod(){
        System.setProperty("video.save.mode", "ALL");
    }

    @Test
    @Video(name = "screenshotElementTest")
    public void screenshotElementTest() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        browserContext.setDefaultTimeout(5000);
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/challenging_dom");
        page.locator(".large-2.columns").click();
    }

    @Test
    public void playwrightRecordTest() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("target/test_video")));
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/login");
        page.locator("#username").fill("tomsmith");
        page.locator("#password").fill("SuperSecretPassword!");
        page.locator("//button").click();
        assertThat(page.locator("//h4")).containsText("Welcome to the Secure Area. When you are done click logout below.");
        page.waitForTimeout(2000);
        page.context().close();
    }

    @Test
    public void playwrightRecordHarTest() throws IOException {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setRecordHarPath(Paths.get("target/har.har")));
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/login");
        page.locator("#username").fill("tomsmith");
        page.locator("#password").fill("SuperSecretPassword!");
        page.locator("//button").click();
        page.context().close();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> harMap = objectMapper.readValue(Paths.get("target/har.har").toFile(), Map.class);
        List<Map<String, Object>> entries = (List<Map<String, Object>>) ((Map<String, Object>) harMap.get("log")).get("entries");
        for (Map<String, Object> entrie : entries){
            Map<String, Object> response = (Map<String, Object>)entrie.get("response");
            int status = (int)response.get("status");
            Map<String, Object> request = (Map<String, Object>)entrie.get("request");
            System.out.println(request.get("url") + " " + status);
            System.out.println("body " + request.get("body"));
        }
    }

    //chromium
    @Test
    public void connectPlaywrightToSelenideTest() throws IOException {
        Selenide.open("https://the-internet.herokuapp.com/login");
        String wsUrl = ((RemoteWebDriver) WebDriverRunner.getWebDriver()).getCapabilities().getCapability("se:cdp").toString();

        Playwright playwright = Playwright.create();
        Page page = playwright.chromium().connectOverCDP(wsUrl).contexts().get(0).pages().get(0);
        page.locator("#username").fill("tomsmith");
        page.locator("#password").fill("SuperSecretPassword!");
        $x("//button").click();
        assertThat(page.locator("//h4")).containsText("Welcome to the Secure Area. When you are done click logout below.");
    }

    //chromium
    @Test
    public void connectSelenideToPlaywrightTest() {
        Playwright playwright = Playwright.create();
        Page page = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setChannel("chrome")
                        .setArgs(List.of("--remote-debugging-port=9222")))
                .newPage();

        Configuration.browser = ChromeDataProvider.class.getName();

        open("https://the-internet.herokuapp.com/login");
        $("#username").setValue("tomsmith");
        $("#password").setValue("SuperSecretPassword!");
        page.locator("//button").click();
    }

    @Test
    public void playwrightRemoteTest() {
        Map<String, String> remote = Map.of("SELENIUM_REMOTE_URL", "http://localhost:4444/wd/hub");

        Playwright playwright = Playwright.create(new Playwright.CreateOptions().setEnv(remote));
        Page page = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false))
                .newPage();

        page.navigate("https://the-internet.herokuapp.com/login");
        page.locator("#username").fill("tomsmith");
        page.locator("#password").fill("SuperSecretPassword!");
        page.locator("//button").click();
    }

    @Test
    public void seleniumRecordDesctopTest() {
        Map<String, String> remote = Map.of("SELENIUM_REMOTE_URL", "http://localhost:4444/wd/hub");

        Playwright playwright = Playwright.create(new Playwright.CreateOptions().setEnv(remote));
        Page page = playwright.chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false))
                .newPage();

        page.navigate("https://the-internet.herokuapp.com/login");
        page.locator("#username").fill("tomsmith");
        page.locator("#password").fill("SuperSecretPassword!");
        page.locator("//button").click();
    }
}
