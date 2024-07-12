package me.manzhos.tests.collections;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import me.manzhos.api.CollectionsApi;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import me.manzhos.endpoints.UsersetsEndpoints;
import me.manzhos.models.AllUsersetsResponse;
import me.manzhos.utils.PropertiesReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class CollectionsContractTest extends BaseTest {

    private String baseUrl;
    private PropertiesReader propertiesReader;
    private CollectionsApi collectionsApi;

    @BeforeMethod
    public void setUp() throws IOException {
        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("collectionUrl");
        collectionsApi = new CollectionsApi();
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsContractTest() throws IOException {
        Response collectionsJson = collectionsApi.getAllCollections(baseUrl, "nl");
        collectionsJson.then().assertThat().
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("contracts/collections.json"));
    }
}
