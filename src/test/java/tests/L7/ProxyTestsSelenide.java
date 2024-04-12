package tests.L7;

import com.browserup.bup.filters.RequestFilter;
import com.browserup.bup.filters.ResponseFilter;
import com.browserup.bup.util.HttpMessageContents;
import com.browserup.bup.util.HttpMessageInfo;
import com.browserup.harreader.model.Har;
import com.browserup.harreader.model.HarEntry;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.proxy.SelenideProxyServer;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class ProxyTestsSelenide  extends TestData{




    @BeforeClass
    public void beforeClass(){
        initFolders();

        Configuration.proxyEnabled = true;
        open();
    }


    @Test(dataProvider = "statusCodesProvider")
    public void test(int statusCode) throws IOException {
        open("https://the-internet.herokuapp.com/status_codes");
        SelenideProxyServer selenideProxyServer = WebDriverRunner.getSelenideProxy();
        selenideProxyServer.getProxy().newHar();
        $x("//ul//li//a[text()='" + statusCode + "']").click();
        pause(3000);
        Har har = selenideProxyServer.getProxy().endHar();
        List<HarEntry> entries = har.getLog().getEntries();
        for(HarEntry entry : entries) {
            if (entry.getRequest().getUrl().equals("https://the-internet.herokuapp.com/status_codes/" + statusCode)) {
                Assert.assertEquals(entry.getResponse().getStatus(), statusCode);
                System.out.println(entry.getRequest().getUrl());
                System.out.println(entry.getResponse().getStatus());
                break;
            }
        }

        har.writeTo(new File("hars/" + statusCode + ".har"));

        pause(1500);
        System.out.println(statusCode);
    }

    @Test
    public void changeUserAgent() {
        WebDriverRunner.getSelenideProxy().getProxy().addRequestFilter(new RequestFilter() {
            @Override
            public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
                request.headers().remove("User-Agent");
                request.headers().add("User-Agent", "Mozilla/5.0 (Windows Phone 10.0; Android 4.2.1; Microsoft; Lumia 640 XL)");
                return null;
            }
        });
        open("https://facebook.com");
        pause(5000);
    }

    @Test
    public void basicAuth() {
        //https://admin:admin@the-internet.herokuapp.com/basic_auth

        String encodededCreds = "Basic " + (Base64.getEncoder().encodeToString("admin:admin".getBytes()));
        WebDriverRunner.getSelenideProxy().getProxy().addHeader("Authorization", encodededCreds);

        open("https://the-internet.herokuapp.com/basic_auth");
        Assert.assertEquals($x("//*[@class='example']//p").getText(),
                "Congratulations! You must have the proper credentials.");
        pause(2000);
        WebDriverRunner.getSelenideProxy().getProxy().removeHeader("Authorization");
        open("https://the-internet.herokuapp.com/basic_auth");
        pause(5000);
    }

    @Test
    public void downloadTest() throws IOException {
        WebDriverRunner.getSelenideProxy().getProxy().addResponseFilter(new ResponseFilter() {
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
        open("http://www.jtricks.com/bits/content_disposition.html");
        pause(3000);
        $(By.linkText("Text file with Content-Type of text/plain.")).click();
        pause(5000);
        File file = new File("content.txt");
        List<String> strings = FileUtils.readLines(file, "UTF-8");
        strings.forEach(System.out::println);
    }
}
