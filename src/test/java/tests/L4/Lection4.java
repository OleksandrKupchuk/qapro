package tests.L4;

import com.codeborne.selenide.*;
import com.codeborne.selenide.commands.Click;
import io.art.tests.L4.CustomCond.Checked;
import io.art.tests.L4.CustomCond.CustomConditions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;

public class Lection4 {

    @Test
    public  void shot() {
        Selenide.open("https://ru.selenide.org/documentation.html");
        Selenide.screenshot("test");
    }

    @Test
    public void customCond(){
        $("").shouldBe(Condition.visible);
        $("").shouldBe(CustomConditions.Checked());
        $("").shouldBe(CustomConditions.VisiblePro());
        $("").shouldBe(Condition.visible).click();
        $("").shouldBe(Condition.visible).execute( new Click());
        //execute(new Click());



        Action action1 = new Actions(webdriver().object()).doubleClick().build();
        actions().doubleClick();
//        $("").sendKeys(Keys.ENTER);
        $("").pressEnter();
        $("").pressEscape();
        $("").pressTab();

        $x("").hover();

        confirm();
        dismiss();
        prompt("I am you text");
    }


    @Test
    public void windows() {
        open("https://the-internet.herokuapp.com/windows");
        $x("//a[text()='Click Here']").shouldBe(Condition.visible).click();


//        System.out.println(webdriver().object().getWindowHandle());
//        System.out.println(webdriver().object().getWindowHandles());
//        webdriver().object().switchTo().window("i am new tab");

        sleep(2000);
        switchTo().window(0); // .window(1);
        sleep(2000);
//        switchTo().newWindow(WindowType.WINDOW);
        open("https://the-internet.herokuapp.com/"); //2
        sleep(2000);
        closeWindow();
        switchTo().window(0);
        sleep(2000);

//        switchTo().frame(1);
//        switchTo().newWindow(WindowType.TAB);
        executeJavaScript("window.open(\"https://ru.selenide.org/quick-start.html\")");
//        sleep(5000);

//        open("https://the-internet.herokuapp.com/windows");
//        $x("//a[text()='Click Here']").shouldBe(Condition.visible).click();
//        sleep(2000);
//        switchTo().window(2);
//        sleep(2000);
//        closeWindow();
//        switchTo().window(1);
//        sleep(2000);
//        closeWindow();
//        sleep(2000);

//        WebDriverRunner. //meta info of WD
    }
}

