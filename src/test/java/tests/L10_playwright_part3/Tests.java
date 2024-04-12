package tests.L10_playwright_part3;

import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitUntilState;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class Tests {

    @Test
    public void uploadTest() throws IOException {
        Faker faker = new Faker();
        String paragraph = faker.lorem().paragraph(5);
        File file = new File("someFile.txt");
        FileUtils.writeStringToFile(file, paragraph);

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/upload");
        page.setInputFiles("#file-upload", file.toPath());
        page.locator("#file-submit").click();
        assertThat(page.locator("#uploaded-files")).containsText(file.getName());
    }

    @Test
    public void downloadTest() throws IOException {
        Faker faker = new Faker();
        String paragraph = faker.lorem().paragraph(5);
        File fileUpload = new File("downloadSomeFile.txt");
        FileUtils.writeStringToFile(fileUpload, paragraph);

        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/upload");
        page.setInputFiles("#file-upload", fileUpload.toPath());
        page.locator("#file-submit").click();

        page.navigate("https://the-internet.herokuapp.com/download");

        Download download = page.waitForDownload(() ->{
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(fileUpload.getName())).click();
        });

        File fileDownload = new File("target", download.suggestedFilename());
        download.saveAs(fileDownload.toPath());
        Assert.assertTrue(FileUtils.contentEquals(fileUpload, fileDownload));
    }

    @Test
    public void keyboardDownUpTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/login");
        page.locator("#username").click();
        page.keyboard().type("some text", new Keyboard.TypeOptions().setDelay(500));

        page.keyboard().down("Control");
        page.keyboard().press("A");
        page.keyboard().up("Control");

        page.keyboard().down("Control");
        page.keyboard().press("c");
        page.keyboard().up("Control");

        page.locator("#password").click();

        page.keyboard().down("Control");
        page.keyboard().press("v");
        page.keyboard().up("Control");
        page.waitForTimeout(2000);
    }

    @Test
    public void keyboardPressTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/login");
        page.locator("#username").click();
        page.keyboard().type("some text", new Keyboard.TypeOptions().setDelay(200));

        page.keyboard().press("Control+A");
        page.keyboard().press("Control+c");

        page.locator("#password").click();

        page.keyboard().press("Control+v");
    }

    @Test
    public void timeoutDefaultTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        browserContext.setDefaultTimeout(5000);
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/login");
        page.locator("#username1").click(new Locator.ClickOptions().setTimeout(1000));
    }

    @Test
    public void loadStateTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        browserContext.setDefaultTimeout(5000);
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/login", new Page.NavigateOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.locator("#username1").click(new Locator.ClickOptions().setTimeout(10000));
    }
}
