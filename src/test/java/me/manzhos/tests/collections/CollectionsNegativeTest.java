package me.manzhos.tests.collections;

import io.restassured.response.Response;
import me.manzhos.api.CollectionsApi;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import me.manzhos.utils.PropertiesReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

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
       assertArtObjectIsNull(specificCollectionResponse);
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkNonExistingInvolvedMaker() throws IOException {
        String collectionInvolvedMaker = "Banurahman Babanummini";
        Response allCollectionsByInvolvedMaker = collectionsApi.getAllCollectionsByInvolvedMaker(baseUrl, "en", collectionInvolvedMaker);

        assertArtObjectIsNull(allCollectionsByInvolvedMaker);
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkNonExistingType() throws IOException {
        String collectionType= "sand";
        Response allCollectionsByType = collectionsApi.getAllCollectionsByType(baseUrl, "en", collectionType);

        assertArtObjectIsNull(allCollectionsByType);
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkNonExistingMaterial() throws IOException {
        String collectionMaterial = "woody foam";
        Response allCollectionsByMaterialResponse = collectionsApi
                .getAllCollectionsByMaterial(baseUrl, "en", collectionMaterial);

        assertArtObjectIsNull(allCollectionsByMaterialResponse);
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkNonExistingQueryPhrase() throws IOException {
        String query = ":+)";
        Response specificCollectionResponse = collectionsApi.searchByQuery(baseUrl, "en", query);

        assertArtObjectIsNull(specificCollectionResponse);
    }

    private void assertArtObjectIsNull(Response response) {
        response.then().statusCode(200).body("artObjects", hasSize(0));
    }
}
