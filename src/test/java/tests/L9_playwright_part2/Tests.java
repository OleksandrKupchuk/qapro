package tests.L9_playwright_part2;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.BoundingBox;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public class Tests {
    @Test(groups = {"release"})
    public void alertTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/javascript_alerts");
        page.onceDialog(alert -> {
            alert.accept();
        });
        page.getByText("Click for JS Alert").click();
        page.onceDialog(alert -> {
            alert.accept("some text");
        });
        page.getByText("Click for JS Prompt").click();
    }

    @Test
    public void frameByUrlTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/nested_frames");
        Frame frame = page.frameByUrl("https://the-internet.herokuapp.com/frame_top");
        FrameLocator rightFrame = frame.frameLocator("[name=frame-right]");
        assertThat(rightFrame.locator("body")).containsText("RIGHT");
        page.navigate("https://the-internet.herokuapp.com/dynamic_loading");
    }

    @Test
    public void frameByNameTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/nested_frames");
        Frame frame = page.frame("frame-top");
        FrameLocator rightFrame = frame.frameLocator("[name=frame-right]");
        assertThat(rightFrame.locator("body")).containsText("RIGHT");
    }

    @Test
    public void windowTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/windows");
        assertThat(page.locator("h3")).containsText("Opening a new window");
        Page secondPage = browserContext.waitForPage(() -> {
            page.getByText("Click Here").click();
        });
        secondPage.close();
    }

    @Test
    public void mouseScrollTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://playwright.dev/java/");
        page.mouse().wheel(0, 600);
        page.waitForTimeout(1000);
        page.mouse().wheel(0, -600);
    }

    @Test
    public void mouseClickTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://playwright.dev/java/");
        BoundingBox button = page.getByText("Get started").boundingBox();
        double x = button.x + (button.width / 2);
        double y = button.y + (button.height / 2);
        page.mouse().click(x, y);
    }

    @Test
    public void mouseDragAndDropTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/drag_and_drop");
        BoundingBox columnA = page.locator("#column-a").boundingBox();
        BoundingBox columnB = page.locator("#column-b").boundingBox();
        double columnAX = columnA.x + (columnA.width / 2);
        double columnAY = columnA.y + (columnA.height / 2);
        double columnBX = columnB.x + (columnB.width / 2);
        double columnBY = columnB.y + (columnB.height / 2);
        page.mouse().move(columnAX, columnAY);
        page.mouse().down();
        page.mouse().move(columnBX, columnBY);
        page.mouse().up();
        assertThat(page.locator("#column-a").locator("header")).containsText("B");
        assertThat(page.locator("#column-b").locator("header")).containsText("A");
    }

    @Test
    public void mouseHoverAndBlurTest(){
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://the-internet.herokuapp.com/hovers");
        page.getByAltText("User Avatar").first().hover();
        page.waitForTimeout(1000);
        page.getByAltText("User Avatar").first().blur();
        page.waitForTimeout(2000);
    }
}
