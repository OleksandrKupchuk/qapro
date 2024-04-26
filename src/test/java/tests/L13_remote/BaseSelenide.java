package tests.L13_remote;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

public class BaseSelenide {
    @BeforeClass
    public void beforeClass(){
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("selenoid:options", new HashMap<String, Object>(){{
            put("enableVideo", true);
            put("enableVNC", true);
        }});
//        desiredCapabilities.setCapability("moon:options", new HashMap<String, Object>(){{
//            put("enableVideo", true);
//            put("enableVNC", true);
//        }});

        Configuration.remote = "http://localhost:4444/wd/hub";
//        Configuration.remote = "http://127.0.0.1:4444/wd/hub";
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = desiredCapabilities;
    }
}
