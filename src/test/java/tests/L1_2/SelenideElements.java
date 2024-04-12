package tests.L1_2;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class SelenideElements  {

    @Test
    public void elements(){
    Configuration.baseUrl = "https://the-internet.herokuapp.com/";

        Selenide.open("/dynamic_controls");
        SelenideElement checkboxInput = $(By.xpath("//div[@id='checkbox']/input")).shouldBe(Condition.visible);
        checkboxInput.click();
        Assert.assertTrue(checkboxInput.is(Condition.checked));
//        Assert.assertTrue(checkbox.is(not(Condition.checked)));
        Assert.assertTrue(checkboxInput.isDisplayed());

//        checkboxInput.parent().text();
//        checkboxInput.parent().getText();
//        checkboxInput.parent().getWrappedElement().getText();
        sleep(5000);

        $x("//form[@id='checkbox-example']//button").shouldBe(Condition.visible).click();
        SelenideElement loadingBar = $(By.id("loading")).shouldBe(Condition.visible).shouldBe(not(Condition.visible), Duration.ofSeconds(20));
        Assert.assertTrue(loadingBar.is(not(visible)));


        WebDriverWait twstwait = new WebDriverWait(webdriver().object(), Duration.ofSeconds(10));
        twstwait.until((ExpectedCondition<Boolean>) input -> true);

//        Action dobleclick = new Actions(webdriver().object().findElement(By.xpath("//form[@id='checkbox-example']//button")).doubleClick().build();
//        Action scroll = new Actions(webdriver().object()).scrollByAmount(10, 10).build();
//        dobleclick.perform();
//        scroll.perform();

//        Configuration.timeout = Duration.ofSeconds(5).toMillis();
//        DynamicControlsPage.open();
//        SelenideElement checkboxInput = $(By.xpath("//div[@id='checkbox']/input"));
////        checkboxInput.shouldBe(Condition.visible).click(ClickOptions.usingDefaultMethod());
//        Assert.assertTrue(checkboxInput.is(not(Condition.checked)));
//        Assert.assertTrue(checkboxInput.isDisplayed());

////        Selenide.screenshot("shot");
//        Assert.fail();
////        Action b = new Actions(webdriver().object()).doubleClick().build();
//
//        $x("//form[@id='checkbox-example']//button").shouldBe(Condition.visible).click();
//        SelenideElement loadingBar = $(By.id("loading")).shouldBe(Condition.visible)
//                .shouldBe(not(Condition.visible), Duration.ofSeconds(20));
//        Assert.assertTrue(loadingBar.is(not(visible)));
//
//        new WebDriverWait(webdriver().object(), Duration.ofSeconds(10));
//        Selenide.Wait().until((ExpectedCondition<Boolean>) input -> true);
//
//
        $$x("//*").shouldHave(CollectionCondition.sizeLessThan(2))
                .filter(and("name for log", text("asd"), text("aaaa")).because("message")); //last / find text
    }
}
