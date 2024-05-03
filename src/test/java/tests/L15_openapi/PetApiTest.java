package tests.L15_openapi;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.response.ResponseBody;
import org.junit.Before;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.PetApi;
import org.openapitools.client.model.Pet;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static org.openapitools.client.GsonObjectMapper.gson;

public class PetApiTest {
    private PetApi api;

    @BeforeTest
    public void createApi() {
        api = ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(
                () -> new RequestSpecBuilder()
                        .setBaseUri("https://petstore3.swagger.io")
                        .setBasePath("/api/v3"))).pet();
    }

    @Test
    public void createPet(){
        Faker faker = new Faker();
        long id = faker.number().numberBetween(0, 10000);
        String name = faker.name().firstName();

        Pet pet = new Pet()
                .id(id)
                .name(name)
                .status(Pet.StatusEnum.AVAILABLE);

        Pet createdPet = api.addPet()
                .body(pet)
                .executeAs(ResponseBody::prettyPeek);
//                .execute(response -> response.then().statusCode(200));

        Assert.assertEquals(createdPet.getId(), id);
    }
}
