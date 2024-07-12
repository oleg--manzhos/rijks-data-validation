package me.manzhos.tests.usersets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import me.manzhos.api.UsersetsApi;
import me.manzhos.base.BaseTest;
import me.manzhos.endpoints.UsersetsEndpoints;
import me.manzhos.models.UsersetResponse;
import me.manzhos.utils.BOMRemover;
import me.manzhos.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UsersetsFunctionalTest extends BaseTest{

    private String baseUrl;
    private PropertiesReader propertiesReader;
    private UsersetsApi usersetsApi;
    private BOMRemover bomRemover;
    private UsersetResponse usersetResponse;

    @BeforeMethod (alwaysRun = true)
    public void getEnvironmentUrl() throws IOException {
        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("usersetUrl");
        usersetsApi = new UsersetsApi();
        bomRemover = new BOMRemover();
        usersetResponse = new UsersetResponse();
    }

    @Test
    public void checkAllUsersetsTest() throws IOException {
        Response allUsersets = given()
                .spec(mainSpecification("nl"))
                .baseUri(baseUrl)
                .when().log().all()
                .get(UsersetsEndpoints.getAllUsersets);
        allUsersets.then().log().all();
        allUsersets.then().assertThat().statusCode(200);
    }

    @Test
    public void checkSpecificUsersetTest() throws IOException {
        String userset = "1664319-mijn-eerste-verzameling";
        String stringWithBom = usersetsApi.getUsersetsApi(baseUrl, userset)
                .then().extract().asString();
        UsersetResponse cleanedResponseBody = bomRemover.removeBOM(usersetResponse, stringWithBom);

        Assert.assertEquals(cleanedResponseBody.getUserSet().getId(), userset);
    }

    // this test is disabled as the response contains ZWNBSP (zero width no-break space ) character
    // that prevents it from being parsed
    @Test(enabled = false)
    public void checkSpecificUsersetTestFailed() throws IOException {
        String userset = "1664319-mijn-eerste-verzameling";
        Response specificUserset = given()
                .spec(mainSpecification("nl"))
                .baseUri(baseUrl)
                .pathParam("set-id", userset)
                .when().log().all()
                .get(UsersetsEndpoints.getUsersetDetails);

        specificUserset.then().log().all();
        specificUserset.then().statusCode(200).body("userSer.id", equalTo(userset));
    }
}
