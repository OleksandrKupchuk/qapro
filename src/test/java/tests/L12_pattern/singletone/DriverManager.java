package tests.L12_pattern.singletone;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverManager {
    private static DriverManager instance = null;
    private static ThreadLocal<WebDriver> driver =new ThreadLocal<>();
    public synchronized static DriverManager getInstance(){
        if(instance == null){
            instance = new DriverManager();
        }
        if(driver.get() == null){
            driver.set(new ChromeDriver());
        }
        return instance;
    }

    //для того щоб не синхронізувати DriverManager кожен раз при виклику, можна це зробити 1 раз при ініцалізації
//    private volatile static DriverManager instance = null;
//    public static DriverManager getInstance(){
//        if (instance == null){
//            synchronized (DriverManager.class){
//                instance = new DriverManager();
//            }
//        }
//        if(driver.get() == null){
//            driver.set(new ChromeDriver());
//        }
//        return instance;
//    }

    //jvm гарнтує що екземпляр буде створений до того як юудь-який потік зможе звернутися до статичної змінної
//    private static DriverManager instance = new DriverManager();
//    public static DriverManager getInstance(){
//        if(driver.get() == null){
//            driver.set(new ChromeDriver());
//        }
//        return instance;
//    }

    public WebDriver getDriver(){
        return driver.get();
    }

    public void close(){
        driver.get().close();
    }
}
