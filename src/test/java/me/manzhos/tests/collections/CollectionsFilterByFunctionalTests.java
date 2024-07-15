package me.manzhos.tests.collections;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import me.manzhos.api.CollectionsApi;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import me.manzhos.enums.SortByEnum;
import me.manzhos.utils.CollectionsApiUtil;
import me.manzhos.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Feature("Check filtering capabilities for all collections: filtering by involved maker, by collection type, by material," +
        "by technique, by dating period, by normalized color, by available images, by top pieces and sorting")
public class CollectionsFilterByFunctionalTests extends BaseTest {

    private String baseUrl;
    private PropertiesReader propertiesReader;
    private CollectionsApi collectionsApi;

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("collectionUrl");
        collectionsApi = new CollectionsApi();
    }
    @Description("Filtering all collections by the artist involved")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsByInvolvedMaker() throws IOException {
        String involvedMaker = "Isaac Weissenbruch";
        Allure.step("Set involved maker to "+ involvedMaker);
        Response allCollectionsResponse = collectionsApi.getAllCollectionsByInvolvedMaker(baseUrl, "en", involvedMaker);
        allCollectionsResponse.then().statusCode(200);
        Allure.step("Check that response status is 200");
        List<String> involvedMakersList = new ArrayList<>();
        involvedMakersList.addAll(allCollectionsResponse.then().extract().path("facets.findAll{it}.facets[0].key"));

        Assert.assertTrue(involvedMakersList.contains(involvedMaker));
        Allure.step("Assert that list of involved makers includes " + involvedMaker);
    }

    @Description("Filtering all collections by type")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionByType() throws IOException {
        String type = "folder (container)";
        Allure.step("Set collection filter to "+ type);
        Response allCollectionsResponse = collectionsApi.getAllCollectionsByType(baseUrl, "en", type);

        List<String> typeList = new ArrayList<>();
        typeList.addAll(allCollectionsResponse.then().extract().path("facets.findAll{it}.facets[1].key"));
        Allure.step("Get the list of used types for the returned collections");

        Assert.assertTrue(typeList.contains(type));
        Allure.step("Assert that list of types includes " + type);
    }

    @Description("Filtering all collections by material")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionByMaterial() throws IOException {
        String material = "Japanese paper (handmade paper)";
        Response allCollectionsResponse = collectionsApi.getAllCollectionsByMaterial(baseUrl, "en", material);

        List<String> materialList = new ArrayList<>();
        materialList.addAll(allCollectionsResponse.then().extract().path("facets.findAll{it}.facets[4].key"));

        Assert.assertTrue(materialList.contains(material));
    }

    @Description("Filtering all collections by technique")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionByTechnique() throws IOException {
        String technique = "jacquard";

        Response allCollectionsByTechniqueResponse = collectionsApi.getAllCollectionsByTechnique(baseUrl, "en", technique);

        List<String> techniqueList = new ArrayList<>();
        techniqueList.addAll(allCollectionsByTechniqueResponse.then().extract().path("facets.findAll{it}.facets[5].key"));

        Assert.assertTrue(techniqueList.contains(technique));
    }

    @Description("Filtering all collections by dating period")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionByDatingPeriod() throws IOException {
        String datingPeriod = "18";
        Response allCollectionsByDatingPeriodResponse = collectionsApi.getAllCollectionsByDatingPeriod(baseUrl, "en", datingPeriod);

        List<String> datingPeriodList = new ArrayList<>();
        datingPeriodList.addAll(allCollectionsByDatingPeriodResponse.then().extract().path("facets.findAll{it}.facets[2].key"));

        Assert.assertTrue(datingPeriodList.contains(datingPeriod));
    }
    @Description("Filtering all collections by normalized colors")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionByNormalizedColors() throws IOException {
        String normalizedColors = "#4279DB";
        Response allCollectionsByNormalizedColorsResponse = collectionsApi.getAllCollectionsByNormalizedColors(baseUrl, "en", normalizedColors);

        List<String> materialList = new ArrayList<>();
        materialList.addAll(allCollectionsByNormalizedColorsResponse.then().extract().path("facets.findAll{it}.facets[6].key"));

        Assert.assertTrue(materialList.contains(normalizedColors));
    }
    @Description("Filtering all collections by displayed image = true")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionByImageOnlyTrue() throws IOException {
        Response allCollectionsByImageOnlyResponse = collectionsApi.getAllCollectionsByImageOnly(baseUrl, "nl", true);
        allCollectionsByImageOnlyResponse.then().statusCode(200);

        //  "true" value should be somewhere or some field should contain it
//        List<String> imageOnlyList = new ArrayList<>();
//        imageOnlyList.addAll(allCollectionsByImageOnlyResponse.then().extract().path("facets.findAll{it}.facets[6].key"));
//
//        Assert.assertTrue(imageOnlyList.contains(Boolean.valueOf("true"));
   }
    @Description("Filtering all collections by displayed image = false")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionByImageOnlyFalse() throws IOException {
        Response allCollectionsByImageOnlyResponse = collectionsApi.getAllCollectionsByImageOnly(baseUrl, "nl", false);
        allCollectionsByImageOnlyResponse.then().statusCode(200);

        //  "false" value should be somewhere or some field should contain it
//        List<String> imageOnlyList = new ArrayList<>();
//        imageOnlyList.addAll(allCollectionsByImageOnlyResponse.then().extract().path("facets.findAll{it}.facets[6].key"));
//
//        Assert.assertTrue(imageOnlyList.contains(Boolean.valueOf("true")));
    }
    @Description("Filtering all collections by top piece = false")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionByTopPiecesFalse() throws IOException {
        Response allCollectionsByImageOnlyResponse = collectionsApi.getAllCollectionsByTopPieces(baseUrl, "nl", false);
        allCollectionsByImageOnlyResponse.then().statusCode(200);

        //  "false" value should be somehow recognizable in the response
//        List<String> topPiecesList = new ArrayList<>();
//        topPiecesList.addAll(allCollectionsByImageOnlyResponse.then().extract().path("facets.findAll{it}.facets[6].key"));
//
//        Assert.assertTrue(topPiecesList.contains(Boolean.valueOf("false")));
    }

    @Description("Filtering all collections by top piece = true")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionByTopPiecesTrue() throws IOException {
        Response allCollectionsByImageOnlyResponse = collectionsApi.getAllCollectionsByTopPieces(baseUrl, "nl", true);
        allCollectionsByImageOnlyResponse.then().statusCode(200);

        //  "true" value should be somehow recognizable in the response
//        List<String> topPiecesList = new ArrayList<>();
//        topPiecesList.addAll(allCollectionsByImageOnlyResponse.then().extract().path("facets.findAll{it}.facets[6].key"));
//
//        Assert.assertTrue(topPiecesList.contains(Boolean.valueOf("true")));
    }
    @Description("Filtering all collections by search filter = relevance")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsFilterByRelevance() throws IOException {
        List<String> expectedRelevanceList = new ArrayList<>(Arrays.asList("BK-1973-482-B", "RP-F-2007-15-169",
                "RP-P-OB-80.958", "RP-T-00-3186-25", "RP-P-OB-81.028", "RP-P-OB-80.979", "RP-F-00-5360-55",
                "BK-NM-12166-H-2", "RP-T-2007-1", "RP-T-00-492-74"));
        Response allCollectionsByRelevanceResponse = collectionsApi
                .getAllCollectionsBySortingParameter(baseUrl, "nl", SortByEnum.RELEVANCE.toString(), "10");
        List<String> actualRelevanceList = new ArrayList();
        actualRelevanceList.addAll(allCollectionsByRelevanceResponse.then().statusCode(200).extract()
                .path("artObjects.findAll{it}.objectNumber"));
        Assert.assertEquals(actualRelevanceList, expectedRelevanceList);
    }

    @Description("Filtering all collections by search filter = object type")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsFilterByObjectType() throws IOException {
        List<String> expectedObjectTypeList = new ArrayList<>(Arrays.asList("SK-A-5003", "SK-A-5002",
                "SK-A-2320-B", "SK-A-2320-C", "SK-C-1701", "NG-C-2011-2", "SK-A-4902",
                "SK-A-4911", "SK-C-311", "BK-14656-310"));
        Response allCollectionsByObjectTypeResponse = collectionsApi
                .getAllCollectionsBySortingParameter(baseUrl, "nl", SortByEnum.OBJECT_TYPE.toString(), "10");
        List<String> actualObjectTypeList = new ArrayList();
        actualObjectTypeList.addAll(allCollectionsByObjectTypeResponse.then().statusCode(200).extract()
                .path("artObjects.findAll{it}.objectNumber"));
        Assert.assertEquals(actualObjectTypeList, expectedObjectTypeList);
    }

    @Description("Filtering all collections by search filter = chronologic")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsFilterByChronologic() throws IOException {
        List<String> expectedChronologicList = new ArrayList<>(Arrays.asList("AK-MAK-36", "AK-MAK-34",
                "AK-MAK-35", "AK-BR-FLA-3", "AK-BR-FLA-7", "AK-BR-FLA-6", "AK-RAK-1996-1",
                "AK-RAK-1996-4", "AK-RAK-1993-2", "AK-RAK-1993-2-2"));
        Response allCollectionsByObjectTypeResponse = collectionsApi
                .getAllCollectionsBySortingParameter(baseUrl, "nl", SortByEnum.CHRONOLOGIC.toString(), "10");
        List<String> actualChronologicList = new ArrayList();
        actualChronologicList.addAll(allCollectionsByObjectTypeResponse.then().statusCode(200).extract()
                .path("artObjects.findAll{it}.objectNumber"));
        Assert.assertEquals(actualChronologicList, expectedChronologicList);
    }

    @Description("Filtering all collections by search filter = achronologic")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsFilterByAchronologic() throws IOException {
        List<String> expectedChronologicList = new ArrayList<>(Arrays.asList("KOG-MP-1-3813A", "BK-16990",
                "AK-MAK-390", "AK-MAK-1168", "AK-RAK-1970-2", "AK-MAK-1479", "AK-MAK-1476",
                "AK-RBK-15951", "AK-MAK-47", "AK-MAK-1284"));
        Response allCollectionsByObjectTypeResponse = collectionsApi
                .getAllCollectionsBySortingParameter(baseUrl, "nl", SortByEnum.ACHRONOLOGIC.toString(), "10");
        List<String> actualChronologicList = new ArrayList();
        actualChronologicList.addAll(allCollectionsByObjectTypeResponse.then().statusCode(200).extract()
                .path("artObjects.findAll{it}.objectNumber"));
        Assert.assertEquals(actualChronologicList, expectedChronologicList);
    }

    @Description("Filtering all collections by search filter = artist desc")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsFilterByArtist() throws IOException {
        Response allCollectionsByArtistResponse = collectionsApi
                .getAllCollectionsBySortingParameter(baseUrl, "nl", SortByEnum.ARTIST_DESC.toString(), "30");
        List<String> expectedArtistOrderList = new ArrayList();
        expectedArtistOrderList.addAll(allCollectionsByArtistResponse.then().statusCode(200).extract()
                .path("artObjects.findAll{it}.principalOrFirstMaker"));
        Assert.assertTrue(CollectionsApiUtil.isListSortedAlphabetically(expectedArtistOrderList, false));
    }

    @Description("Filtering all collections by search filter = artist")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsFilterByArtistAsc() throws IOException {
        Response allCollectionsByArtistResponse = collectionsApi
                .getAllCollectionsBySortingParameter(baseUrl, "nl", SortByEnum.ARTIST.toString(), "30");
        List<String> expectedArtistOrderList = new ArrayList();
        expectedArtistOrderList.addAll(allCollectionsByArtistResponse.then().statusCode(200).extract()
                .path("artObjects.findAll{it}.principalOrFirstMaker"));
        Assert.assertTrue(CollectionsApiUtil.isListSortedAlphabetically(expectedArtistOrderList, true));
    }

    @Description("Filtering all the collections, using combined parameters")
    @Test(retryAnalyzer = RetryConfig.class)
    public void checkCollectionsFilterByCombinedParameters() throws IOException {
        Response allCollectionsByCombinedParametersResponse = collectionsApi
                .getAllCollectionByCombinedParameters(baseUrl, "nl", SortByEnum.RELEVANCE.toString(),
                        "0", "100", "Georg Rueter", "aluminium",
                        "demonstratiemodel", "gelatineglasnegatief", false,
                        "#4019B1","19", "false");
    }
}