package tests.L4.CustomCond;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementCondition;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;

public class VisblePro extends WebElementCondition {
    public VisblePro() {
        super("visible");
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, WebElement element) {
        boolean displayed = element.isDisplayed();
        return new CheckResult(displayed, displayed ? "visible" : "hidden");
    }
}
