package tests.L1_2;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class FirstTest {


    @Test
    public void firstTest() {
        Configuration.baseUrl = "https://the-internet.herokuapp.com";
//        Configuration.browserSize = "100*100";

        Configuration.browser = io.art.tests.L1_2.ChromeDriverInit.class.getName();
//        Configuration.browser = ChromeDriverInit.class.getName();

//        ChromeDriver chromeDriver = new ChromeDriver();
//
//        WebDriverRunner.setWebDriver(chromeDriver); //re-use

//        webdriver().object().manage().window().maximize();



//        Configuration.remote = "http://localhost:4444/wd/hub";
//        Configuration.timeout = Duration.ofSeconds(15).toMillis();

//        Configuration.browser = "firefox";
//        FirefoxOptions options = new FirefoxOptions();
//        options.setCapability("browserVersion", "122.0");
//        Map<String, Object> selenoidOptions = new HashMap<String, Object>();
//        selenoidOptions.put("enableVNC", true);
//        options.setCapability("selenoid:options", selenoidOptions);
//
//        Configuration.browserCapabilities = options;


//        ChromeOptions options = new ChromeOptions();

//        Configuration.browserVersion = "119";  // v115 >

//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--headless=new");
//        chromeOptions.setPageLoadTimeout(Duration.ofSeconds(5));
//        Configuration.clickViaJs = true;
//        Configuration.fastSetValue = true;

//        Configuration.headless = true;
//        Configuration.fileDownload = FileDownloadMode.FOLDER;

        Configuration.downloadsFolder = "testdownload-1";



//        ChromeDriver chromeDriver = new ChromeDriver();
//
//        WebDriverRunner.setWebDriver(chromeDriver); //re-use

        sleep(5000);
//        open("/javascript_alerts");
        open("/download");
//        webdriver().object().manage().window().maximize();
        $(By.xpath("//a[text()='luminoslogo.png']")).download();

        sleep(10000);

//        chromeDriver.quit();
    }
}
