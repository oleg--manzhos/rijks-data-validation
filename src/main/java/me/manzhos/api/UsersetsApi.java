package me.manzhos.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import me.manzhos.base.BaseTest;
import me.manzhos.endpoints.UsersetsEndpoints;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class UsersetsApi extends BaseTest {

    @Step("Get all usersets")
    public Response getAllUsersetsApi(String baseUrl, String culture) throws IOException {
        Response allUsersets = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .get(UsersetsEndpoints.getAllUsersets);
        allUsersets.then().log().all();

        return allUsersets;
    }

    @Step("Get all usersets with {page} pages and {pageSize} size per page")
    public Response getAllUsersetsWithPaginationApi(String baseUrl, String culture, String page, String pageSize) throws IOException {
        Response allUsersets = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .queryParam("page", page)
                .queryParam("pageSize", pageSize)
                .when().log().all()
                .get(UsersetsEndpoints.getAllUsersets);
        allUsersets.then().log().all();

        return allUsersets;
    }

    @Step("Get the specific user set: {userset}")
    public Response getUsersetsApi(String baseUrl, String userset) throws IOException {
        Response specificUserset = given()
                .spec(mainSpecification("nl"))
                .baseUri(baseUrl)
                .pathParam("set-id", userset)
                .when().log().all()
                .get(UsersetsEndpoints.getUsersetDetails);
        specificUserset.then().log().all();

        return specificUserset;
    }
}
