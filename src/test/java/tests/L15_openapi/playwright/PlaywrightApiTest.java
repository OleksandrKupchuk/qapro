package tests.L15_openapi.playwright;

import com.github.javafaker.Faker;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.json.JSONObject;
import org.openapitools.client.model.Order;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.OffsetDateTime;
import java.util.Map;

public class PlaywrightApiTest {
    Playwright playwright;
    APIRequestContext context;

    @BeforeTest
    public void setup(){
        playwright = Playwright.create(new Playwright.CreateOptions()
                .setEnv(Map.of("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1")));
        APIRequest.NewContextOptions contextOptions = new APIRequest.NewContextOptions()
                .setBaseURL("https://petstore3.swagger.io/api/v3/");

        context = playwright.request().newContext(contextOptions);
    }

    //Playwright не може виконати метод /post тому що звертається до приватного поля dateTime в класі OffsetDateTime
    @Test
    public void createOrder(){
        Faker faker = new Faker();
        long idOrder = faker.number().numberBetween(0, 10000);
        long idPet = faker.number().numberBetween(0, 10000);

        Order order = new Order()
                .id(idOrder)
                .petId(idPet)
                .status(Order.StatusEnum.APPROVED);

        order.setShipDate(OffsetDateTime.now());

        RequestOptions requestOptions = RequestOptions.create();
        requestOptions.setData(order);

        APIResponse response = context.post("./store/order", requestOptions);
        String responseAsText = response.body().toString();

        JSONObject jsonObject = new JSONObject(responseAsText);
        Assert.assertEquals(jsonObject.get("id"), idOrder);
    }

    @Test
    public void getOrderById(){
        RequestOptions requestOptions = RequestOptions.create();

        APIResponse response = context.get("./store/order/" + 1, requestOptions);
        String responseAsText = response.body().toString();

        JSONObject jsonObject = new JSONObject(responseAsText);
        Assert.assertEquals(jsonObject.get("id"), 1);
    }

    @Test
    public void deleteOrderById(){
        RequestOptions requestOptions = RequestOptions.create();
        context.delete("./store/order/" + 2, requestOptions);
    }
}
