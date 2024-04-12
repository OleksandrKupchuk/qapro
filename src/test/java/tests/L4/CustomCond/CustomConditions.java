package tests.L4.CustomCond;

import com.codeborne.selenide.WebElementCondition;

public class CustomConditions {

    public static WebElementCondition clickable() {
        return new Clickable();

    }

    public static WebElementCondition Checked() {
        return new Checked();
    }

    public static WebElementCondition VisiblePro() {
        return  new VisblePro();
    }

}
