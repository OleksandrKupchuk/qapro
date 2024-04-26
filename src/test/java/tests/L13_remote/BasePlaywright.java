package tests.L13_remote;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import tests.L12_pattern.singletone.DriverManager;

public class BasePlaywright {
    private static BasePlaywright instance = null;
    private static ThreadLocal<Page> page =new ThreadLocal<>();
    public synchronized static BasePlaywright getInstance(){
        if(instance == null){
            instance = new BasePlaywright();
        }
        if(page.get() == null){
            page.set(Playwright.create()
                    .chromium()
                    .connect("ws://127.0.0.1:4444/playwright/chromium/playwright-1.42.0?headless=false&enableVNC=true")
                    .newPage());
        }
        return instance;
    }

    public Page getPage() {return page.get();}
}
