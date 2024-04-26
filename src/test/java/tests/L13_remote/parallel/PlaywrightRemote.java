package tests.L13_remote.parallel;

import com.microsoft.playwright.*;
import org.testng.annotations.Test;
import tests.L13_remote.BasePlaywright;

public class PlaywrightRemote {
    @Test
    public void keyboardDownUpTest(){
        Page page = BasePlaywright.getInstance().getPage();
        page.navigate("https://the-internet.herokuapp.com/login");
        page.locator("#username").click();
        page.keyboard().type("some text", new Keyboard.TypeOptions().setDelay(500));

        page.keyboard().down("Control");
        page.keyboard().press("A");
        page.keyboard().up("Control");

        page.keyboard().down("Control");
        page.keyboard().press("c");
        page.keyboard().up("Control");

        page.locator("#password").click();

        page.keyboard().down("Control");
        page.keyboard().press("v");
        page.keyboard().up("Control");
        page.waitForTimeout(2000);
    }
}
