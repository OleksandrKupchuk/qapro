package tests.L4.CustomCond;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.WebElementCondition;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;

public class Checked extends WebElementCondition {

    public Checked() {
        super("checked");
    }

    @Nonnull
    @Override
    public CheckResult check(Driver driver, WebElement element) {
        boolean checked = element.isSelected();
        return new CheckResult(checked, checked ? "checked" : "unchecked");
    }
}
