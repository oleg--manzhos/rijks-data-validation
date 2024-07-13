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
        //calling all the collections
        Response allCollectionsResponse = collectionsApi.getAllCollections(baseUrl, "nl");

        //assertions
        allCollectionsResponse.then().statusCode(200)
                .body("artObjects.findAll{it}.size()", equalTo(10),
        "artObjects.find{it}.id", startsWith("nl"));
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkAllCollectionsEn() throws IOException {
        Response allCollectionsResponse = collectionsApi.getAllCollections(baseUrl, "en");
        //assertions
        allCollectionsResponse.then().statusCode(200)
                .body("artObjects.findAll{it}.size()", equalTo(10),
                "artObjects.find{it}.id", startsWith("en"));
    }

    @Test(dataProviderClass = CollectionsPaginationDataProvider.class, dataProvider = "pagination", retryAnalyzer = RetryConfig.class)
    public void checkAllUsersetsPagination(String page, String sizePerPage, int expectedAmount) throws IOException {
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
    public void checkSpecificCollectionEn() throws IOException {
        String collectionObject = "BK-17496";
        String title = "Blue Macaw";
        Response specificCollectionResponse = collectionsApi
                .getSpecificCollection(baseUrl, "en", collectionObject);
       //assertions
        specificCollectionResponse.then().statusCode(200)
                .body("artObject.objectNumber", equalTo(collectionObject),
                "artObject.title", equalTo(title),
                "artObject.language", equalTo("en"));
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkSpecificCollectionNl() throws IOException {
        String collectionObject = "BK-17496";
        String title = "Blauwe ara";
        Response specificCollectionResponse = collectionsApi
                .getSpecificCollection(baseUrl, "nl", collectionObject);
       // assertions
        specificCollectionResponse.then().statusCode(200)
                .body("artObject.objectNumber", equalTo(collectionObject),
                "artObject.title", equalTo(title),
                "artObject.language", equalTo("nl"));
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionTiles() throws IOException {
        String objectNumber = "BK-1975-81";
        Response specificCollectionResponse = collectionsApi
                .getTileOfSpecificCollection(baseUrl, "nl", objectNumber);
        // assertions
        specificCollectionResponse.then().statusCode(200);
        specificCollectionResponse.then().extract().response().prettyPrint();
    }
}