package me.manzhos.tests.usersets;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import me.manzhos.api.UsersetsApi;
import me.manzhos.endpoints.UsersetsEndpoints;
import me.manzhos.models.AllUsersetsResponse;
import me.manzhos.utils.BOMRemover;
import me.manzhos.utils.PropertiesReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class UsersetsContractTest {

    private String baseUrl;
    private String apiKey;
    private PropertiesReader propertiesReader;
    private UsersetsApi usersetsApi;
    private BOMRemover bomRemover;
    private AllUsersetsResponse allUsersetsResponse;

    @BeforeMethod
    public void getEnvironmentUrl() throws IOException {
        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("usersetUrl");
        apiKey = propertiesReader.getValueFromConfig("api_key");
        usersetsApi = new UsersetsApi();
        bomRemover = new BOMRemover();
        allUsersetsResponse = new AllUsersetsResponse();
    }

    @Test
    public void checkUsersetsContractTest() throws IOException {
        String json = usersetsApi.getAllUsersetsApi(baseUrl, apiKey).asString();
        AllUsersetsResponse allUsersResponse = bomRemover.removeBOM(allUsersetsResponse, json);
        Response response = given().spec(usersetsApi.mainSpecification("nl"))
                .body(allUsersResponse)
                .when().get(UsersetsEndpoints.getAllUsersets);
        response.then().statusCode(200);
        response.then().assertThat().
            body(JsonSchemaValidator.matchesJsonSchemaInClasspath("contracts/usersets.json"));
    }
}
