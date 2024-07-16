package me.manzhos.tests.bibliographical;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import me.manzhos.api.BibloigraphicalApi;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.*;

public class BibloigraphicalNegativeTest extends BaseTest {

    private BibloigraphicalApi bibliographicalApi;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        bibliographicalApi = new BibloigraphicalApi();
    }

    @Description("Check biblographical endpoint returns data")
    @Test(retryAnalyzer = RetryConfig.class, enabled = false)
    public void checkBibliographicalInvalidQuerySynTest() throws IOException {
        String query = "===";
        String error = "Query syntax error";
        Response biblographicResponse =  bibliographicalApi
                .getBiblographicData("1.1", "searchRetrieve", "2", query);
        biblographicResponse.then().statusCode(200).contentType(ContentType.XML)
                .body(hasXPath("//diagnostics//diag:message", equalTo(error)));
    }
}
