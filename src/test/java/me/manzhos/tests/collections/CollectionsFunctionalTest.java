package me.manzhos.tests.collections;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
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

@Feature("Collections tests")
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
    @Description("All collections are returned using NL culture")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkAllCollectionsNl() throws IOException {
        Response allCollectionsResponse = collectionsApi.getAllCollections(baseUrl, "nl");

        allCollectionsResponse.then().statusCode(200)
                .body("artObjects.findAll{it}.size()", equalTo(10),
        "artObjects.find{it}.id", startsWith("nl"));
    }

    @Description("All collections are returned using EN culture")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkAllCollectionsEn() throws IOException {
        Response allCollectionsResponse = collectionsApi.getAllCollections(baseUrl, "en");

        allCollectionsResponse.then().statusCode(200)
                .body("artObjects.findAll{it}.size()", equalTo(10),
                "artObjects.find{it}.id", startsWith("en"));
    }
    @Description("Check the pagination and page size results returned for all collections")
    @Step("Pagination is set for {page} page, {pageSize} page size and expected amount is {expectedAmount}")
    @Test(dataProviderClass = CollectionsPaginationDataProvider.class, dataProvider = "pagination", retryAnalyzer = RetryConfig.class)
    public void checkAllCollectionsPagination(String page, String sizePerPage, int expectedAmount) throws IOException {
        Response allCollectionsPagination = collectionsApi
                .getAlCollectionsWithPagination(baseUrl, "nl", page, sizePerPage);

        allCollectionsPagination.then().statusCode(200)
                .body("artObjects.findAll{it.id}.size()", equalTo(expectedAmount));
        Allure.step("Assertion that the obtained size of collections in the response corresponds to expectedAmount");
    }

    @Test(retryAnalyzer = RetryConfig.class, dataProviderClass = SearchPhraseDataProvider.class, dataProvider = "search-term")
    public void checkSearchByFields(String searchQuery) throws IOException {
        Response allCollectionsResponse = collectionsApi.searchByQuery(baseUrl, "nl", searchQuery);

        Assert.assertTrue(collectionsApi.isPresent(allCollectionsResponse, searchQuery));
        Allure.step("Assertion that the search query is present at least for one of the keys");
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionTiles() throws IOException {
        String objectNumber = "BK-1975-81";
        Allure.step("Object number is set to " + objectNumber);
        Response specificCollectionResponse = collectionsApi
                .getTileOfSpecificCollection(baseUrl, "nl", objectNumber);

        specificCollectionResponse.then().statusCode(200);
        specificCollectionResponse.then().extract().response().prettyPrint();
        Allure.step("The result is displayed");
    }
}