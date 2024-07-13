package me.manzhos.tests.collections;

import io.restassured.response.Response;
import me.manzhos.api.CollectionsApi;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import me.manzhos.utils.PropertiesReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class CollectionsNegativeTest extends BaseTest {

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
    public void checkNonExistingCollectionEn() throws IOException {
        String collectionObject = "BK-174960";
        Response specificCollectionResponse = collectionsApi
                .getSpecificCollection(baseUrl, "en", collectionObject);
        //assertions
        specificCollectionResponse.then().statusCode(200)
                .body("artObject", equalTo(null));
    }
}
