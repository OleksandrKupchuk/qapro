package tests.L8_playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PlaywrightTests {


    @Test
    public void nulTest() {
        Playwright playwright = Playwright.create();
        Browser launch = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = launch.newContext(new Browser.NewContextOptions());
        browserContext.pages();
        Page page = browserContext.newPage();
        page.navigate("https://google.com");
        page.waitForTimeout(5000);
    }

    @Test
    public void firstTest() {
        Playwright playwright = Playwright.create();
        Browser launch = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext = launch.newContext(new Browser.NewContextOptions());
        Page page = browserContext.newPage();

        page.navigate("https://google.com");
        page.reload();
        browserContext.onRequest(request -> {
            System.out.println("browserContext " + request.url());
        });
        page.waitForTimeout(5000);
//        page.screenshot(new Page.ScreenshotOptions());
//        page.video();



//        playwright.chromium().connect(); dbPort statrted browser
//        playwright.chromium().connectOverCDP(); selenide -> PLW  -> selenide

    }

    @Test
    public void roleTest() {
        Playwright playwright = Playwright.create();
        Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)).newPage();
        page.navigate("https://testpages.eviltester.com/styled/basic-html-form-test.html");
        page.waitForTimeout(1000);

        page.locator("input[name=username]").fill("test");
//        page.getByRole(AriaRole.TEXTBOX)
//                .filter(new Locator.FilterOptions()
//                        .setHas(page.locator("[name=username]")))
//                .fill("test text");
        page.waitForTimeout(1000);
//        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("cancel"));
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("cancel")).click();
        page.waitForTimeout(1000);
        assertThat(page.locator("input[name=username]")).containsText("");
    }

    @Test
    public void placeholderTest() {
        Playwright playwright = Playwright.create();
        Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newPage();
        page.navigate("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml5_input_placeholder");
        page.waitForTimeout(1000);

        Frame frame = page.frame("iframeResult");

        String testValue = "111-11-111";
        frame.getByPlaceholder("123-45-678").fill(testValue);
        frame.getByRole(AriaRole.BUTTON, new Frame.GetByRoleOptions().setName("submit")).click();
        assertThat(frame.locator(".w3-container.w3-large.w3-border")).containsText(testValue);
        page.waitForTimeout(1000);
    }

    @Test
    public void altTest() {
        Playwright playwright = Playwright.create();
        Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newPage();
        page.navigate("https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_image_test");
        page.waitForTimeout(1000);

        assertThat(page.frame("iframeResult")
                .getByAltText("Girl in a jacket"))
                .hasAttribute("src", "img_girl.jpg");


    }

    @Test
    public void dataTestIdTest() {
        Playwright playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-automation");
        Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newPage();
        page.navigate("https://www.shutterstock.com/ru/pricing");
        page.waitForTimeout(1000);
        System.out.println(page.getByTestId("PricingCard_OD_tabTitleContainer").textContent());

        ///--------------------------
        page.getByTestId("PricingCard_OD_tabTitleContainer").all();
        page.getByTestId("PricingCard_OD_tabTitleContainer").nth(1); // starts from 1 index
        page.getByTestId("PricingCard_OD_tabTitleContainer").allTextContents();
    }

    @Test
    public void alertsTest() {
        Playwright playwright = Playwright.create();
        Page page = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newPage();
        page.navigate("https://the-internet.herokuapp.com/javascript_alerts");
        page.waitForTimeout(2000);
//
        String text = "text";
//
//        AtomicReference<String> message = new AtomicReference<>("");
//
//        page.onceDialog(alert -> {
//            message.set(alert.message());
//            alert.accept(text);
//        });
//
        page.evaluate("() => jsAlert()");
//        page.waitForTimeout(2000);
//        page.getByText("Click for JS Prompt").click();
//        System.out.println(message);
        page.waitForTimeout(5000);
        assertThat(page.locator("#result")).containsText("You entered: " + text);
    }
}
