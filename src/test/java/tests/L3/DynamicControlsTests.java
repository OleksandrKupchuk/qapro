package tests.L3;

import tests.L3.core.Base;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static tests.L3.pages.DynamicControlsPage.*;

public class DynamicControlsTests extends Base {

    @Test()
    public void test(){
        open();
        clickCheckbox();
        clickButton();
//        Assert.assertTrue(loadingBarProcessing(20).is(not(visible)));
    }
}
