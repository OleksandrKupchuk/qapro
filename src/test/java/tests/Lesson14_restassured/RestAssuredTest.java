package tests.Lesson14_restassured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import tests.Lesson14_restassured.model.RequestEmployee;
import tests.Lesson14_restassured.model.ResponseEmployee;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
public class RestAssuredTest {
    @BeforeTest
    public void setup(){
        RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .addCookie("myCookie", "dfsardasd")
                .addHeader("header", "dasdasd")
                .build();
    }

    @Test
    public void getAllEmployees(){
        Response response = RestAssured.given().log().all().get("/employees");
        response.then().body("data[0].employee_name", equalTo("Tiger Nixon"));
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void getEmployee(){
        Response response = RestAssured.get("/employee/{id}", 6);
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void createEmployeeWithoutModel(){
        String body = "{\"name\":\"some name\",\"salary\":\"12000\",\"age\":\"23\"}";

        Response response = RestAssured.given()
                        .body(body)
                        .post("/create");
        response.then().statusCode(200);

        JSONObject jsonObject = new JSONObject(response.asString());
        Integer id = (Integer) ((JSONObject)jsonObject.get("data")).get("id");

        response.prettyPrint();
    }

    @Test
    public void createEmployeeWithModel(){
        RequestEmployee body = new RequestEmployee()
                .builder()
                .name("Oleg")
                .salary("100000")
                .age("29")
                .build();

        Response response = RestAssured.given()
                .body(body)
                .post("/create");
        response.then().statusCode(200);

        ResponseEmployee responseEmployee = response.as(ResponseEmployee.class);
        responseEmployee.getData().getId();

        response.prettyPrint();
    }

    @Test
    public void assertJsonScheme(){
        Response response = RestAssured.get("/employees");
        response.then().statusCode(200);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("employeesJsonScheme.json"));
        response.prettyPrint();
    }
}
