package tests.L15_openapi;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.response.ResponseBody;
import org.junit.Before;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.PetApi;
import org.openapitools.client.api.StoreApi;
import org.openapitools.client.model.Order;
import org.openapitools.client.model.Pet;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static org.openapitools.client.GsonObjectMapper.gson;

public class StoreApiTest {

    private StoreApi api;

    @BeforeTest
    public void createApi() {
        api = ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(
                () -> new RequestSpecBuilder()
                        .setBaseUri("https://petstore3.swagger.io")
                        .setBasePath("/api/v3"))).store();

    }

    @Test
    public void getInventory(){
         api.getInventory()
                .execute(ResponseBody::prettyPeek)
                 .then().statusCode(200);
    }

    @Test
    public void creteOrder(){
        Faker faker = new Faker();
        long idOrder = faker.number().numberBetween(0, 10000);
        long idPet = faker.number().numberBetween(0, 10000);

        Order order = new Order()
                .id(idOrder)
                .petId(idPet)
                .status(Order.StatusEnum.APPROVED);

        Order createdOrder = api.placeOrder()
                .body(order)
                .executeAs(ResponseBody::prettyPeek);

        Assert.assertEquals(createdOrder.getStatus(), Order.StatusEnum.APPROVED);
        Assert.assertEquals(createdOrder.getId(), idOrder);
        Assert.assertEquals(createdOrder.getPetId(), idPet);
    }

    @Test
    public void deleteOrder(){
        Faker faker = new Faker();
        long idOrder = faker.number().numberBetween(0, 10000);
        long idPet = faker.number().numberBetween(0, 10000);

        Order order = new Order()
                .id(idOrder)
                .petId(idPet)
                .status(Order.StatusEnum.APPROVED);

        api.placeOrder()
                .body(order)
                .executeAs(ResponseBody::prettyPeek);

        api.deleteOrder()
                .orderIdPath(idOrder)
                .execute(ResponseBody::prettyPeek);

        api.getOrderById()
                .orderIdPath(idOrder)
                .execute(response -> response.then().statusCode(404));
    }
}
