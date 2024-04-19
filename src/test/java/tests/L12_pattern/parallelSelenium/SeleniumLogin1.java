package tests.L12_pattern.parallelSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.L12_pattern.fabric_method.BrowserType;
import tests.L12_pattern.fabric_method.FabricMethod;
import tests.L12_pattern.singletone.DriverManager;

public class SeleniumLogin1 {
    @Test
    public void loginTest(){
//        WebDriver driver = DriverManager.getInstance().getDriver();
        WebDriver driver = FabricMethod.getDriver(BrowserType.CHROME);
        driver.get("https://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.xpath("//button")).click();
        String text = driver.findElement(By.xpath("//h4")).getText();
        Assert.assertEquals(text, "Welcome to the Secure Area. When you are done click logout below.");
        driver.close();
    }
}
