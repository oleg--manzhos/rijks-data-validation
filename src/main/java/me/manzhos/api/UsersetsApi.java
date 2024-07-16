package me.manzhos.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import me.manzhos.base.BaseTest;
import me.manzhos.endpoints.UsersetsEndpoints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static io.restassured.RestAssured.given;
/**
 * UsersetsApi class provides methods to interact with the usersets API.
 * It includes methods for retrieving usersets with various parameters.
 */
public class UsersetsApi extends BaseTest {
    /**
     * Retrieves a list of all usersets.
     *
     * @param baseUrl The base URL of the API.
     * @param culture The culture parameter for the request.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */

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

    /**
     * Retrieves all usersets with pagination.
     *
     * @param baseUrl The base URL of the API.
     * @param culture The culture parameter for the request.
     * @param page    The page number for pagination.
     * @param pageSize The number of items per page.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Retrieves a specific userset by its ID.
     *
     * @param baseUrl The base URL of the API.
     * @param userset The ID of the userset.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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