package me.manzhos.tests.usersets;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import me.manzhos.api.UsersetsApi;
import me.manzhos.base.RetryConfig;
import me.manzhos.endpoints.UsersetsEndpoints;
import me.manzhos.models.AllUsersetsResponse;
import me.manzhos.utils.BOMRemover;
import me.manzhos.utils.PropertiesReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@Feature("Contract tests")
public class UsersetsContractTest {

    private String baseUrl;
    private String apiKey;
    private PropertiesReader propertiesReader;
    private UsersetsApi usersetsApi;
    private BOMRemover bomRemover;
    private AllUsersetsResponse allUsersetsResponse;

    @BeforeMethod(alwaysRun = true)
    public void getEnvironmentUrl() throws IOException {
        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("usersetUrl");
        apiKey = propertiesReader.getValueFromConfig("api_key");
        usersetsApi = new UsersetsApi();
        bomRemover = new BOMRemover();
        allUsersetsResponse = new AllUsersetsResponse();
    }

    @Description("Contract test to check the Usersets endpoint")
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
