package me.manzhos.tests.bibliographical;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import me.manzhos.api.BibloigraphicalApi;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.xml.HasXPath.hasXPath;

@Feature("Bibliographic data")
public class BibloigraphicalTest extends BaseTest {

    private BibloigraphicalApi bibliographicalApi;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        bibliographicalApi = new BibloigraphicalApi();
    }

    @Description("Check biblographical endpoint returns data")
    @Test(testName = "BibliographicalTest", retryAnalyzer = RetryConfig.class)
    public void bibliographicalTest() throws IOException {
        String query = "Boucher";
        Response biblographicResponse =  bibliographicalApi
                .getBiblographicData("1.1", "searchRetrieve", "2", query);
        biblographicResponse.then().statusCode(200).contentType(ContentType.XML)
                .body(hasXPath("//datafield[@tag='260']/subfield[@code='b'][contains(text(), '"+ query+"')]"));
    }
}
