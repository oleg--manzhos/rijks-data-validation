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
    private final String involvedMaker = "involvedMaker";
    private final String objectType = "type";
    private final String material = "material";
    private final String technique = "technique";
    private final String datingPeriod = "f.dating.period";
    private final String normalizedColors = "f.normalized32Colors.hex";
    private final String imageOnly = "imgonly";
    private final String topPieces = "toppieces";
    private final String sortBy = "s";


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

    @Step("Get collection by {involvedMaker} involved maker")
    public Response  getAllCollectionsByInvolvedMaker(String baseUrl, String culture, String involvedMaker) throws IOException {
        Response allCollectionInvolvedMakerResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.involvedMaker, involvedMaker)
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionInvolvedMakerResponse.then().log().all();

        return allCollectionInvolvedMakerResponse;
    }

    @Step("Get collection by {type} type")
    public Response getAllCollectionsByType(String baseUrl, String culture, String type) throws IOException {
        Response allCollectionInvolvedMakerResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.objectType, type)
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionInvolvedMakerResponse.then().log().all();

        return allCollectionInvolvedMakerResponse;
    }

    @Step("Get collection by {type} type")
    public Response getAllCollectionsByMaterial(String baseUrl, String culture, String material) throws IOException {
        Response allCollectionByMaterialResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.material, material)
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionByMaterialResponse.then().log().all();

        return allCollectionByMaterialResponse;
    }

    @Step("Get collection by {technique} technique")
    public Response getAllCollectionsByTechnique (String baseUrl, String culture, String technique) throws IOException {
        Response allCollectionByTechniqueResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.technique, technique)
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionByTechniqueResponse.then().log().all();

        return allCollectionByTechniqueResponse;

    }

    @Step("Get collection by {normalizedColors} normalized colors")
    public Response getAllCollectionsByNormalizedColors (String baseUrl, String culture, String normalizedColors) throws IOException {
        Response allCollectionByNormalizedColorsResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.normalizedColors, normalizedColors)
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionByNormalizedColorsResponse.then().log().all();

        return allCollectionByNormalizedColorsResponse;
    }

    @Step("Get collection by {datingPeriod} dating period")
    public Response getAllCollectionsByDatingPeriod (String baseUrl, String culture, String datingPeriod) throws IOException {
        Response tileOfSpecificCollectionsResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.datingPeriod, datingPeriod)
                .get(CollectionsEndpoint.getTiles);
        tileOfSpecificCollectionsResponse.then().log().all();

        return tileOfSpecificCollectionsResponse;
    }

    @Step("Get collection when imageOnly parameter is {imageOnly}")
    public Response getAllCollectionsByImageOnly (String baseUrl, String culture, boolean imageOnly) throws IOException {
        Response tileOfSpecificCollectionsResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.imageOnly, imageOnly)
                .get(CollectionsEndpoint.getAllCollections);
        tileOfSpecificCollectionsResponse.then().log().all();

        return tileOfSpecificCollectionsResponse;
    }

    @Step("Get collection when imageOnly parameter is {imageOnly}")
    public Response getAllCollectionsByTopPieces (String baseUrl, String culture, boolean topPieces) throws IOException {
        Response allCollectionsByTopPiecesResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.topPieces, topPieces)
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionsByTopPiecesResponse.then().log().all();

        return allCollectionsByTopPiecesResponse;
    }

    @Step("Get collection when relevance parameter s is {relevance}")
    public Response getAllCollectionsByTopPieces (String baseUrl, String culture, String relevance) throws IOException {
        Response allCollectionsByTopPiecesResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.topPieces, topPieces)
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionsByTopPiecesResponse.then().log().all();

        return allCollectionsByTopPiecesResponse;
    }

    public Response getAllCollectionsBySortingParameter(String baseUrl, String culture, String sortBy, String pageSize) throws IOException {
        Response allCollectionsByRelevanceResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.sortBy, sortBy)
                .queryParam(this.pageSize, pageSize)
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionsByRelevanceResponse.then().log().all();

        return allCollectionsByRelevanceResponse;
    }
}
