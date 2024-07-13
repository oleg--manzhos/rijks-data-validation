package me.manzhos.api;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import me.manzhos.base.BaseTest;
import me.manzhos.endpoints.CollectionsEndpoint;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

public class CollectionsApi extends BaseTest {

    private final String page = "p";
    private final String pageSize = "ps";
    private final String objectNumber = "object-number";
    private final String search = "q";

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
                .queryParam(this.page, page)
                .queryParam(this.pageSize, pageSize)
                .when().log().all()
                .get(CollectionsEndpoint.getAllCollections);
        allUsersets.then().log().all();

        return allUsersets;
    }

    @Step("Search by {q}")
    public Response searchByQuery(String baseUrl, String culture, String searchQuery) throws IOException {
        Response searchByQueryCollectionsResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(search, searchQuery)
                .get(CollectionsEndpoint.getAllCollections);
        searchByQueryCollectionsResponse.then().log().all();

        return searchByQueryCollectionsResponse;
    }

    @Step("Get collection with {culture} culture and id {collectionId}")
    public Response getSpecificCollection(String baseUrl, String culture, String collectionId) throws IOException {
        Response specificCollectionsResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .pathParam(objectNumber, collectionId)
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
                .pathParam(objectNumber, collectionId)
                .get(CollectionsEndpoint.getTiles);
        tileOfSpecificCollectionsResponse.then().log().all();

        return tileOfSpecificCollectionsResponse;
    }
    @Step("Check if at least one of the specified fields contains the search result in each object of an array response")
    public boolean isPresent(Response response, String searchQuery) throws IOException {
        JsonPath jsonPath = response.jsonPath();
        List<Map<String, Object>> artObjects = jsonPath.getList("artObjects");

        System.out.println(artObjects.toString());

        return artObjects.stream().allMatch(obj ->
                Stream.of(
                        (String) obj.get("objectNumber"),
                        (String) obj.get("id"),
                        (String) obj.get("title"),
                        (String) obj.get("principalOrFirstMaker"),
                        (String) obj.get("longTitle"),
                        obj.get("productionPlaces") != null ? obj.get("productionPlaces").toString() : null
                ).anyMatch(field -> field != null && field.contains(searchQuery))
        );
    }
}
