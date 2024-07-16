package me.manzhos.api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import me.manzhos.endpoints.BibliographicalEndpoint;
import me.manzhos.utils.PropertiesReader;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BibloigraphicalApi extends BaseTest{

    private final String version = "version";
    private final String operation = "operation";
    private final String maximumRecords = "maximumRecords";
    private final String query = "query";
    private String baseUrl;
    private PropertiesReader propertiesReader;

    @Step("Call the biblographic data endpoint")
    public Response getBiblographicData(String version, String operation, String maximumRecords, String query) throws IOException {

        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("bibliographicUrl");

        Response bibliographicResponse = given()
                .spec(bibliographicalSpecification())
                .baseUri(baseUrl)
                .contentType(ContentType.XML)
                .when().log().all()
                .queryParam(this.version, version)
                .queryParam(this.operation, operation)
                .queryParam(this.maximumRecords, maximumRecords)
                .queryParam(this.query, query)
                .get(BibliographicalEndpoint.getBibliographicalData);
        bibliographicResponse.then().log().all();

        return bibliographicResponse;
    }
}
