package tests.L7;

import com.browserup.bup.BrowserUpProxy;
import com.browserup.bup.BrowserUpProxyServer;
import com.browserup.bup.client.ClientUtil;
import com.browserup.bup.filters.RequestFilter;
import com.browserup.bup.filters.ResponseFilter;
import com.browserup.bup.proxy.CaptureType;
import com.browserup.bup.util.HttpMessageContents;
import com.browserup.bup.util.HttpMessageInfo;
import com.browserup.harreader.model.Har;
import com.browserup.harreader.model.HarEntry;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class ProxyTestsSel extends TestData {
    BrowserUpProxy bmp;
    WebDriver driver;

    @BeforeClass
    public void beforeClass(){
        initFolders();
        bmp = new BrowserUpProxyServer();
        bmp.setTrustAllServers(true);

        bmp.setHarCaptureTypes(CaptureType.getAllContentCaptureTypes());
        bmp.setHarCaptureTypes(CaptureType.getHeaderCaptureTypes());
//        bmp.setMitmDisabled(true);

        bmp.start(0); //any opened port

        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(bmp); // proxy obj for driver

        seleniumProxy.setSslProxy("localhost:" + bmp.getPort()); // dont set up ssl by default (M1)

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setCapability("proxy", seleniumProxy);

        chromeOptions.setAcceptInsecureCerts(true);

        driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void close(){
        driver.quit();
    }

    @Test(dataProvider = "statusCodesProvider")
    public void test(int statusCode){
        driver.get("https://the-internet.herokuapp.com/status_codes");
        bmp.newHar();
        driver.findElement(By.xpath("//ul//li//a[text()='" + statusCode + "']")).click();
        driver.navigate().refresh();
        pause(1000);
        driver.navigate().refresh();
        pause(1000);
        driver.navigate().refresh();
        pause(1000);
        driver.navigate().refresh();
        pause(3000);
        Har har = bmp.endHar();
        List<HarEntry> entries = har.getLog().getEntries();
        for(HarEntry entry : entries) {
            if (entry.getRequest().getUrl().equals("https://the-internet.herokuapp.com/status_codes/" + statusCode)) {
                Assert.assertEquals(entry.getResponse().getStatus(), statusCode);
                System.out.println(entry.getRequest().getUrl());
                System.out.println(entry.getResponse().getStatus());
                break;
            }
        }
            try {
                har.writeTo(new File("hars/" + statusCode + ".har"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            pause(1500);
        System.out.println(statusCode);
    }

    @Test
    public void changeUserAgent() {
        bmp.addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                request.headers().remove("User-Agent");
                request.headers().add("User-Agent", "Mozilla/5.0 (Windows Phone 10.0; Android 4.2.1; Microsoft; Lumia 640 XL)");
                return null;
            }
        });
        driver.get("https://facebook.com");
        pause(5000);
    }

    @Test
    public void basicAuth() {
        //https://admin:admin@the-internet.herokuapp.com/basic_auth

        String encodededCreds = "Basic " + (Base64.getEncoder().encodeToString("admin:admin".getBytes()));
        bmp.addHeader("Authorization", encodededCreds);

        driver.get("https://the-internet.herokuapp.com/basic_auth");
        Assert.assertEquals(driver.findElement(By.xpath("//*[@class='example']//p")).getText(),
                "Congratulations! You must have the proper credentials.");
        pause(1000);

        bmp.removeHeader("Authorization");
        driver.get("https://the-internet.herokuapp.com/basic_auth");
        pause(5000);
    }

    @Test
    public void downloadTest() throws IOException {
        bmp.addResponseFilter(new ResponseFilter() {
            @Override
            public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                String fileName;
                String fileContent;
                HttpHeaders headers = response.headers();
                if (headers.get("Content-Disposition") != null
                    && headers.get("Content-Type").equals("text/plain")) {
                    fileName = headers.get("Content-Disposition").split(";")[1].split("=")[1].trim();
                    fileContent = contents.getTextContents();
                    File file = new File(fileName);
                    try {
                        FileUtils.write(file, fileContent, "UTF-8");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        driver.get("http://jtricks.com/bits/content_disposition.html");
        pause(3000);
        driver.findElement(By.linkText("Text file with Content-Type of text/plain.")).click();
        pause(5000);
        File file = new File("content.txt");
        List<String> strings = FileUtils.readLines(file, "UTF-8");
        strings.forEach(System.out::println);
    }

}
