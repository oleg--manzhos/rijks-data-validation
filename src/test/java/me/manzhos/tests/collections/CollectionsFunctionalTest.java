package me.manzhos.tests.collections;

import io.restassured.response.Response;
import me.manzhos.api.CollectionsApi;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import me.manzhos.dataproviders.CollectionsPaginationDataProvider;
import me.manzhos.dataproviders.SearchPhraseDataProvider;
import me.manzhos.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class CollectionsFunctionalTest extends BaseTest {

    private String baseUrl;
    private PropertiesReader propertiesReader;
    private CollectionsApi collectionsApi;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("collectionUrl");
        collectionsApi = new CollectionsApi();
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkAllCollectionsNl() throws IOException {

        Response allCollectionsResponse = collectionsApi.getAllCollections(baseUrl, "nl");

        allCollectionsResponse.then().statusCode(200)
                .body("artObjects.findAll{it}.size()", equalTo(10),
        "artObjects.find{it}.id", startsWith("nl"));
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkAllCollectionsEn() throws IOException {
        Response allCollectionsResponse = collectionsApi.getAllCollections(baseUrl, "en");

        allCollectionsResponse.then().statusCode(200)
                .body("artObjects.findAll{it}.size()", equalTo(10),
                "artObjects.find{it}.id", startsWith("en"));
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsByInvolvedMaker() throws IOException {
        String involvedMaker = "Isaac Weissenbruch";
        Response allCollectionsResponse = collectionsApi.getAllCollectionsByInvolvedMaker(baseUrl, "en", involvedMaker);
        allCollectionsResponse.then().statusCode(200);

        List<String> involvedMakers = new ArrayList<>();
        involvedMakers.addAll(allCollectionsResponse.then().extract().path("facets.findAll{it}.facets[0].key"));

        Assert.assertTrue(involvedMakers.contains(involvedMaker));
    }

    @Test
    public void checkCollectionByType() throws IOException {
        String type = "folder (container)";
        Response allCollectionsResponse = collectionsApi.getAllCollectionsByType(baseUrl, "en", type);

        List<String> typeList = new ArrayList<>();
        typeList.addAll(allCollectionsResponse.then().extract().path("facets.findAll{it}.facets[1].key"));

        Assert.assertTrue(typeList.contains(type));
    }

    @Test
    public void checkCollectionByMaterial() throws IOException {
        String material = "Japanese paper (handmade paper)";
        Response allCollectionsResponse = collectionsApi.getAllCollectionsByMaterial(baseUrl, "en", material);

        List<String> materialList = new ArrayList<>();
        materialList.addAll(allCollectionsResponse.then().extract().path("facets.findAll{it}.facets[4].key"));

        Assert.assertTrue(materialList.contains(material));
    }

    @Test
    public void checkCollectionByTechnique() throws IOException {
        String technique = "jacquard";
        Response allCollectionsByTechniqueResponse = collectionsApi.getAllCollectionsByTechnique(baseUrl, "en", technique);

        List<String> materialList = new ArrayList<>();
        materialList.addAll(allCollectionsByTechniqueResponse.then().extract().path("facets.findAll{it}.facets[5].key"));

        Assert.assertTrue(materialList.contains(technique));
    }

    @Test
    public void checkCollectionByDatingPeriod() throws IOException {
        String datingPeriod = "18";
        Response allCollectionsByTechniqueResponse = collectionsApi.getAllCollectionsByDatingPeriod(baseUrl, "en", datingPeriod);

        List<String> materialList = new ArrayList<>();
        materialList.addAll(allCollectionsByTechniqueResponse.then().extract().path("facets.findAll{it}.facets[2].key"));

        Assert.assertTrue(materialList.contains(datingPeriod));
    }

    @Test(dataProviderClass = CollectionsPaginationDataProvider.class, dataProvider = "pagination", retryAnalyzer = RetryConfig.class)
    public void checkAllCollectionsPagination(String page, String sizePerPage, int expectedAmount) throws IOException {
        Response allCollectionsPagination = collectionsApi
                .getAlCollectionsWithPagination(baseUrl, "nl", page, sizePerPage);

        allCollectionsPagination.then().statusCode(200)
                .body("artObjects.findAll{it.id}.size()", equalTo(expectedAmount));
    }

    @Test(retryAnalyzer = RetryConfig.class, dataProviderClass = SearchPhraseDataProvider.class, dataProvider = "search-term")
    public void checkSearchByFields(String searchQuery) throws IOException {
        Response allCollectionsResponse = collectionsApi.searchByQuery(baseUrl, "nl", searchQuery);

        Assert.assertTrue(collectionsApi.isPresent(allCollectionsResponse, searchQuery));
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionTiles() throws IOException {
        String objectNumber = "BK-1975-81";

        Response specificCollectionResponse = collectionsApi
                .getTileOfSpecificCollection(baseUrl, "nl", objectNumber);

        specificCollectionResponse.then().statusCode(200);
        specificCollectionResponse.then().extract().response().prettyPrint();
    }
}