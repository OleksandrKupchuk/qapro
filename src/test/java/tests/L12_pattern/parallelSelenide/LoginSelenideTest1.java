package tests.L12_pattern.parallelSelenide;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.L12_pattern.fabric_method.BrowserType;
import tests.L12_pattern.fabric_method.FabricMethodSelenide;

import static com.codeborne.selenide.Selenide.*;

public class LoginSelenideTest1 {
    @Test
    public void loginTest(){
        FabricMethodSelenide.setBrowser(BrowserType.FIREFOX);
        open("https://the-internet.herokuapp.com/login");
        $(By.id("username")).sendKeys("tomsmith");
        $(By.id("password")).sendKeys("SuperSecretPassword!");
        $x("//button").click();
        String text = $x("//h4").getText();
        Assert.assertEquals(text, "Welcome to the Secure Area. When you are done click logout below.");
    }
}
