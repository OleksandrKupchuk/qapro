package tests.L3.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.*;

public class DynamicControlsPage {

    public static DynamicControlsPage open(){
        Selenide.open("/dynamic_controls");
        return page(DynamicControlsPage.class);
    }

    public static void clickCheckbox() {
        $(By.xpath("//div[@id='checkbox']/input")).shouldBe(Condition.visible).click();
    }

    public static void clickButton() {
        $x("//form[@id='checkbox-example']//button").shouldBe(Condition.visible).click();
    }

    public static SelenideElement loadingBarProcessing(int timeout){
        return  $(By.id("loading")).shouldBe(Condition.visible).shouldBe(not(Condition.visible), Duration.ofSeconds(timeout));
    }
}
