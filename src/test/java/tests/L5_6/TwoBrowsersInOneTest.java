package tests.L5_6;

import com.codeborne.selenide.*;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.*;

public class TwoBrowsersInOneTest {

@Test
    public void twoBrowsers() {
    SelenideDriver selenideDriver1 = new SelenideDriver(new SelenideConfig());
    SelenideDriver selenideDriver2 = new SelenideDriver(new SelenideConfig());

    selenideDriver1.open("https://the-internet.herokuapp.com/dynamic_controls");
    selenideDriver1.$(By.xpath("//div[@id='checkbox']/input")).shouldBe(Condition.visible).click();
    selenideDriver1.$x("//form[@id='checkbox-example']//button").shouldBe(Condition.visible).click();
    selenideDriver1.$(By.id("loading")).shouldBe(Condition.visible).shouldBe(not(Condition.visible), Duration.ofSeconds(20));

    selenideDriver2.open("https://the-internet.herokuapp.com/dynamic_controls");
    selenideDriver2.$(By.xpath("//div[@id='checkbox']/input")).shouldBe(Condition.visible).click();
    selenideDriver2.$x("//form[@id='checkbox-example']//button").shouldBe(Condition.visible).click();
    selenideDriver2.$(By.id("loading")).shouldBe(Condition.visible).shouldBe(not(Condition.visible), Duration.ofSeconds(20));

    selenideDriver1.open("https://the-internet.herokuapp.com/");
    sleep(2000);
    }

    @Test
    public void twoBrowsersLambda() {
    open("https://the-internet.herokuapp.com/dynamic_controls");
    SelenideDriver selenideDriver2 = new SelenideDriver(new SelenideConfig());


    Selenide.using(selenideDriver2.getAndCheckWebDriver(), () -> {
        open("https://the-internet.herokuapp.com/dynamic_controls");
        $(By.xpath("//div[@id='checkbox']/input")).shouldBe(Condition.visible).click();
        $x("//form[@id='checkbox-example']//button").shouldBe(Condition.visible).click();
        $(By.id("loading")).shouldBe(Condition.visible).shouldBe(not(Condition.visible), Duration.ofSeconds(20));
    });
//        open("https://the-internet.herokuapp.com/dynamic_controls");
        $(By.xpath("//div[@id='checkbox']/input")).shouldBe(Condition.visible).click();
        $x("//form[@id='checkbox-example']//button").shouldBe(Condition.visible).click();
        $(By.id("loading")).shouldBe(Condition.visible).shouldBe(not(Condition.visible), Duration.ofSeconds(20));
    }
}
