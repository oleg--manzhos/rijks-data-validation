package me.manzhos.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import jdk.jfr.Description;
import me.manzhos.base.BaseTest;
import me.manzhos.endpoints.CollectionsEndpoint;
import me.manzhos.endpoints.UsersetsEndpoints;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CollectionsApi extends BaseTest {

    @Step("Get the list of all collections")
    public Response getAllCollections(String baseUrl, String culture) throws IOException {
        Response allCollectionsResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionsResponse.then().log().all();

        return allCollectionsResponse;
    }

    @Step("Get all collections with {page} pages and {pageSize} size per page")
    public Response getAlCollectionsWithPagination(String baseUrl, String culture, String page, String pageSize) throws IOException {
        Response allUsersets = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .queryParam("p", page)
                .queryParam("ps", pageSize)
                .when().log().all()
                .get(CollectionsEndpoint.getAllCollections);
        allUsersets.then().log().all();

        return allUsersets;
    }

    @Step("Get collection with {culture} culture and id {collectionId}")
    public Response getSpecificCollection(String baseUrl, String culture, String collectionId) throws IOException {
        Response specificCollectionsResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .pathParam("object-number", collectionId)
                .get(CollectionsEndpoint.getCollectionDetails);
        specificCollectionsResponse.then().log().all();

        return specificCollectionsResponse;
    }

    @Step("Get collection with id {collectionId}")
    public Response getTileOfSpecificCollection(String baseUrl, String culture, String collectionId) throws IOException {
        Response tileOfSpecificCollectionsResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .pathParam("object-number", collectionId)
                .get(CollectionsEndpoint.getTiles);
        tileOfSpecificCollectionsResponse.then().log().all();

        return tileOfSpecificCollectionsResponse;
    }
}
