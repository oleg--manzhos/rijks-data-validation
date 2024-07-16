package me.manzhos.api;

import io.qameta.allure.Step;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import me.manzhos.base.BaseTest;
import me.manzhos.endpoints.CollectionsEndpoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static io.restassured.RestAssured.given;

/**
 * CollectionsApi class provides methods to interact with the collections API.
 * It includes methods for retrieving collections with various parameters.
 */

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

    /**
     * CollectionsApi class provides methods to interact with the collections API.
     * It includes methods for retrieving collections with various parameters.
     */
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

    /**
     * Retrieves all collections with pagination.
     *
     * @param baseUrl The base URL of the API.
     * @param culture The culture parameter for the request.
     * @param page    The page number for pagination.
     * @param pageSize The number of items per page.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Searches collections by a query parameter.
     *
     * @param baseUrl     The base URL of the API.
     * @param culture     The culture parameter for the request.
     * @param searchQuery The search query parameter.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Retrieves a specific collection by its ID.
     *
     * @param baseUrl      The base URL of the API.
     * @param culture      The culture parameter for the request.
     * @param collectionId The ID of the collection.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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


    /**
     * Retrieves the tile of a specific collection by its ID.
     *
     * @param baseUrl      The base URL of the API.
     * @param culture      The culture parameter for the request.
     * @param collectionId The ID of the collection.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Checks if at least one of the specified fields contains the search result in each object of an array response.
     *
     * @param response    The response from the API.
     * @param searchQuery The search query parameter.
     * @return True if at least one field contains the search result, otherwise false.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Retrieves all collections by the involved maker.
     *
     * @param baseUrl     The base URL of the API.
     * @param culture     The culture parameter for the request.
     * @param involvedMaker The involved maker parameter.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Retrieves all collections by the object type.
     *
     * @param baseUrl The base URL of the API.
     * @param culture The culture parameter for the request.
     * @param type    The object type parameter.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Retrieves all collections by the material used.
     *
     * @param baseUrl The base URL of the API.
     * @param culture The culture parameter for the request.
     * @param material The material parameter.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
    @Step("Get collection by {material} material")
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

/**
 * Retrieves all collections by the technique used.
 *
 * @param baseUrl The base URL of the API.
 * @param culture The culture parameter for the request.
 * @param technique The technique parameter
 * @return The response from the API.
 *    /**
 *      * Retrieves all collections by normalized colors.
 *      *
 *      * @param baseUrl The base URL of the API.
 *      * @param culture The culture parameter for the request.
 *      * @param normalizedColors The normalized colors parameter.
 *      * @return The response from the API.
 *      * @throws IOException if an I/O error occurs.
 */
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

    /**
     * Retrieves all collections by normalized colors.
     *
     * @param baseUrl The base URL of the API.
     * @param culture The culture parameter for the request.
     * @param normalizedColors The normalized colors parameter.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */

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

    /**
     * Retrieves all collections by dating period.
     *
     * @param baseUrl The base URL of the API.
     * @param culture The culture parameter for the request.
     * @param datingPeriod The dating period parameter.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
    @Step("Get collection by {datingPeriod} dating period")
    public Response getAllCollectionsByDatingPeriod (String baseUrl, String culture, String datingPeriod) throws IOException {
        Response tileOfSpecificCollectionsResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .when().log().all()
                .queryParam(this.datingPeriod, datingPeriod)
                .get(CollectionsEndpoint.getAllCollections);
        tileOfSpecificCollectionsResponse.then().log().all();

        return tileOfSpecificCollectionsResponse;
    }

    /**
     * Retrieves all collections when the imageOnly parameter is set.
     *
     * @param baseUrl The base URL of the API.
     * @param culture The culture parameter for the request.
     * @param imageOnly The imageOnly parameter.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Retrieves all collections when the topPieces parameter is set.
     *
     * @param baseUrl  The base URL of the API.
     * @param culture  The culture parameter for the request.
     * @param topPieces The topPieces parameter.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
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

    /**
     * Retrieves all collections by sorting parameter and page size.
     *
     * @param baseUrl  The base URL of the API.
     * @param culture  The culture parameter for the request.
     * @param sortBy   The sorting parameter.
     * @param pageSize The size of a page.
     * @return The response from the API.
     * @throws IOException if an I/O error occurs.
     */
    @Step("Get collection when {sortBy} sorting parameter and {pageSize} size of a page")
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

    @Step("Filter all collections by all available parameters")
    public Response getAllCollectionByCombinedParameters(String baseUrl, String culture, String sortBy, String page, String pageSize,
                                          String involvedMaker, String material, String type, String technique,
                                          boolean topPieces, String normalizedColor, String datingPeriod, String imageOnly) throws IOException {

        Map<String,Object> qParameters = new HashMap<>();
        if (!pageSize.isEmpty())
            qParameters.put(this.sortBy, sortBy);
        if (!pageSize.isEmpty())
            qParameters.put(this.pageSize, pageSize);
        if (!topPieces)
            qParameters.put(this.topPieces, topPieces);
        if (!imageOnly.isEmpty())
            qParameters.put(this.imageOnly, imageOnly);
        if (!type.isEmpty())
            qParameters.put(this.objectType, type);
        if (!technique.isEmpty())
            qParameters.put(this.technique, technique);
        if (!normalizedColor.isEmpty())
            qParameters.put(this.normalizedColors, normalizedColor);
        if (!datingPeriod.isEmpty())
            qParameters.put(this.datingPeriod, datingPeriod);
        if(!page.isEmpty())
            qParameters.put(this.page, page);
        if(!involvedMaker.isEmpty())
            qParameters.put(this.involvedMaker, involvedMaker);
        if(!material.isEmpty())
            qParameters.put(this.material, material);

        Response allCollectionsByCombinedQueryResponse = given()
                .spec(mainSpecification(culture))
                .baseUri(baseUrl)
                .queryParams(qParameters)
                .when().log().all()
                .get(CollectionsEndpoint.getAllCollections);
        allCollectionsByCombinedQueryResponse.then().log().all();

        return allCollectionsByCombinedQueryResponse;
    }
}