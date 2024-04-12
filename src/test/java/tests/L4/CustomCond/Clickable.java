package tests.L4.CustomCond;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementCondition;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;

public class Clickable extends WebElementCondition {
    public Clickable(){
        super("clickable");
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, WebElement element) {
        WebElement elementVisible = element.isDisplayed() ? element : null;
        return new CheckResult(elementVisible != null && element.isEnabled(), true);
    }
}
