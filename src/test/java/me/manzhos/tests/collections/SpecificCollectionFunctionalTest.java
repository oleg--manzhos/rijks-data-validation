package me.manzhos.tests.collections;

import io.restassured.response.Response;
import me.manzhos.api.CollectionsApi;
import me.manzhos.base.RetryConfig;
import me.manzhos.utils.PropertiesReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class SpecificCollectionFunctionalTest {

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
    public void checkSpecificCollectionEn() throws IOException {
        String collectionObject = "BK-17496";
        String title = "Blue Macaw";

        Response specificCollectionResponse = collectionsApi
                .getSpecificCollection(baseUrl, "en", collectionObject);

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

        specificCollectionResponse.then().statusCode(200)
                .body("artObject.objectNumber", equalTo(collectionObject),
                        "artObject.title", equalTo(title),
                        "artObject.language", equalTo("nl"));
    }
}
