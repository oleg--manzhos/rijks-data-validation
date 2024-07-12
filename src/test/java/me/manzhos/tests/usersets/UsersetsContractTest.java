package me.manzhos.tests.usersets;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import me.manzhos.endpoints.UsersetsEndpoints;
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

    @BeforeMethod
    public void getEnvironmentUrl() throws IOException {
        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("usersetUrl");
        apiKey = propertiesReader.getValueFromConfig("api_key");
    }

    @Test
    public void UsersetsContractTest() throws IOException {
        Response jsonSchema = given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .pathParam("culture", "nl")
                .queryParam("key", apiKey)
                .when().log().all()
                .get(UsersetsEndpoints.getAllUsersets);

        jsonSchema.then().assertThat().
            body(JsonSchemaValidator.matchesJsonSchemaInClasspath("contracts/usersets.json"));
    }
}
