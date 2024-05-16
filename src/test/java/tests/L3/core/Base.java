package tests.L3.core;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeSuite;

public class Base {

    @BeforeSuite
    public void setUp() {
        Configuration.baseUrl = "https://the-internet.herokuapp.com";
        Configuration.browser = "chrome";
    }
}
