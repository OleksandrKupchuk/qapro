package tests.L16_okhttp;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.json.JSONObject;
import org.openapitools.client.model.Order;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tests.L16_okhttp.adapter.OffsetDateTimeAdapter;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.time.OffsetDateTime;

public class OkHttpStoreTest {
    OkHttpClient client;
    String baseUrl = "petstore3.swagger.io";

    private HttpUrl.Builder getHttp(){
        return new HttpUrl.Builder()
                .scheme("https")
                .host(baseUrl)
                .addPathSegment("api")
                .addPathSegment("v3")
                .addPathSegment("store");
    }

    @BeforeTest
    public void setup(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Test()
    public void getInventory() throws IOException {
        HttpUrl httpUrl = getHttp().addPathSegment("inventory")
                .build();

        Request getInventory = new Request.Builder().url(httpUrl).build();

        Response response = client.newCall(getInventory).execute();
        ResponseBody responseBody = response.body();

        Gson gson = new Gson();
        JsonObject jsonResponse = gson.fromJson(responseBody.string(), JsonObject.class);
    }

    @Test
    public void createOrderGsonAdapter() throws IOException {
        Faker faker = new Faker();
        long orderId = faker.number().numberBetween(0, 10000);
        long petId = faker.number().numberBetween(0, 10000);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeAdapter())
                .create();

        Order order = new Order()
                .id(orderId)
                .petId(petId)
                .status(Order.StatusEnum.PLACED)
                .shipDate(OffsetDateTime.now());

        String json = gson.toJson(order);

        HttpUrl httpUrl = getHttp()
                .addPathSegment("order")
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Request createOrder = new Request.Builder()
                .url(httpUrl)
                .post(requestBody)
                .build();

        Response response = client.newCall(createOrder).execute();
        ResponseBody responseBody = response.body();
        Order responseOrder = gson.fromJson(responseBody.string(), Order.class);
        System.out.println(responseOrder);
    }

    @Test
    public void createOrderJson() throws IOException {
        String jsonOrder = "{\n" +
                "  \"id\": 10,\n" +
                "  \"petId\": 198772,\n" +
                "  \"quantity\": 7,\n" +
                "  \"shipDate\": \"2024-05-03T16:31:02.503Z\",\n" +
                "  \"status\": \"approved\",\n" +
                "  \"complete\": true\n" +
                "}";

        HttpUrl httpUrl = getHttp()
                .addPathSegment("order")
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonOrder);

        Request createOrder = new Request.Builder()
                .url(httpUrl)
                .post(requestBody)
                .build();

        Response response = client.newCall(createOrder).execute();
        ResponseBody responseBody = response.body();

        JSONObject jsonObject = new JSONObject(responseBody.string());
    }

    @Test
    public void deleteOrderGsonAdapter() throws IOException {
        Faker faker = new Faker();
        long orderId = faker.number().numberBetween(0, 10000);
        long petId = faker.number().numberBetween(0, 10000);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new OffsetDateTimeAdapter())
                .create();

        Order order = new Order()
                .id(orderId)
                .petId(petId)
                .status(Order.StatusEnum.PLACED)
                .shipDate(OffsetDateTime.now());

        String json = gson.toJson(order);

        HttpUrl httpUrl = getHttp()
                .addPathSegment("order")
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), json);

        Request createOrder = new Request.Builder()
                .url(httpUrl)
                .post(requestBody)
                .build();
        client.newCall(createOrder).execute();

//        delete
        HttpUrl httpUrlDelete = getHttp()
                .addPathSegment("order")
                .addPathSegment(order.getId().toString())
                .build();

        Request delete = new Request.Builder()
                .url(httpUrlDelete)
                .delete()
                .build();
        client.newCall(delete).execute();

//        getOrderById

        HttpUrl httpUrlById = getHttp()
                .addPathSegment("order")
                .addPathSegment(order.getId().toString())
                .build();

        Request getOrderById = new Request.Builder()
                .url(httpUrlById)
                .build();

        Response response = client.newCall(getOrderById).execute();
        Assert.assertEquals(response.code(), 404);
    }
}
