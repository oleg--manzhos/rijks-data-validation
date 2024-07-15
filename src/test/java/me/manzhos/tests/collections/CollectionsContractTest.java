package me.manzhos.tests.collections;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import me.manzhos.api.CollectionsApi;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import me.manzhos.utils.PropertiesReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
@Feature("Contract tests")
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
    @Description("Contract test for all collections")
    @Test(retryAnalyzer = RetryConfig.class, testName = "Collections contract test")
    public void checkCollectionsContractTest() throws IOException {
        Response collectionsJson = collectionsApi.getAllCollections(baseUrl, "nl");
        collectionsJson.then().assertThat().
                body(JsonSchemaValidator.matchesJsonSchemaInClasspath("contracts/collections.json"));
        Allure.step("Assert collection API correspond to its schema from \"contracts/collections.json\"");
    }
}
