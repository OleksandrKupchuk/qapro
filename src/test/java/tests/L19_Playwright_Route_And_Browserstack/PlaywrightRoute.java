package tests.L19_Playwright_Route_And_Browserstack;

import com.google.common.collect.Iterables;
import com.microsoft.playwright.*;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PlaywrightRoute {
    Page page = Playwright.create().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)).newPage();

    @Test
    public void testBaseAuth(){
        page.route("**/basic_auth", route -> {
            Map<String, String> headers = route.request().headers();
            headers.put("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes()));
            route.resume(new Route.ResumeOptions().setHeaders(headers));
        });

        page.navigate("https://the-internet.herokuapp.com/basic_auth");
        assertThat(page.locator("h3")).containsText("Basic Auth");

        page.unroute("**/basic_auth");
        page.navigate("https://the-internet.herokuapp.com/basic_auth");
    }

    @Test
    public void testStatusCode(){
        int statusCode = 200;
        page.route(Pattern.compile("/status_codes/\\d\\d\\d"), route -> {
            APIResponse fetch = route.fetch();
            int status = fetch.status();
            List<String> list = Arrays.asList(fetch.url().split("/"));
            String last = Iterables.getLast(list);
            Assert.assertEquals(status, Integer.parseInt(last));
            route.resume();
        });

        page.navigate("https://the-internet.herokuapp.com/status_codes/" + statusCode);
        page.unroute(Pattern.compile("/status_codes/\\d\\d\\d"));
    }

    @Test
    public void testDownload() throws IOException {
        page.route("**/download-text", route -> {
            APIResponse fetch = route.fetch();
            Map<String, String> headers = fetch.headers();
            if(headers.get("Content-Disposition".toLowerCase()) != null && headers.get("Content-Type".toLowerCase()).equals("text/plain")){
                String fileName = headers.get("Content-Disposition".toLowerCase()).split(";")[1].split("=")[1].trim();
                String text = fetch.text();
                File file = new File(fileName);
                try {
                    FileUtils.write(file, text, Charset.defaultCharset());
                }catch (IOException e){
                    throw new RuntimeException(e);
                }
            }
            route.resume();
        });

        page.navigate("http://www.jtricks.com/bits/content_disposition.html");
        page.locator("//*[@id=\"content\"]/div/ul[3]/li[2]/a").click();
        page.waitForTimeout(2000);
        File file = new File("content.txt");
        List<String> strings = FileUtils.readLines(file, "UTF-8");
        strings.forEach(System.out::println);
        page.unroute("**/download-text");
    }

    @Test
    public void testTest(){
        page.route("**/*", route -> {
            Map<String, String> headers = route.request().headers();
            headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 14_5_1 like Mac OS X) WebKit/8611 (KHTML, like Gecko) Mobile/18E212 [FBAN/FBIOS;FBDV/iPhone13,2;FBMD/iPhone;FBSN/iOS;FBSV/14.5.1;FBSS/3;FBID/phone;FBLC/es_ES;FBOP/5]");
            route.resume(new Route.ResumeOptions().setHeaders(headers));
        });

        page.navigate("https://www.youtube.com");
        page.unroute("**/*");
    }
}
