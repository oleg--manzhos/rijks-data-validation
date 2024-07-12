package me.manzhos.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import me.manzhos.base.BaseTest;
import me.manzhos.endpoints.UsersetsEndpoints;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class UsersetsApi extends BaseTest {

    @Step("Get the specific user set: {userset}")
    public Response getUsersetsApi(String baseUrl, String userset) throws IOException {
        Response specificUserset = given()
                .spec(mainSpecification("nl"))
                .baseUri(baseUrl)
                .pathParam("set-id", userset)
                .when().log().all()
                .get(UsersetsEndpoints.getUsersetDetails);
        specificUserset.then().log().all();
        specificUserset.then().statusCode(200);
        return specificUserset;
    }
}
