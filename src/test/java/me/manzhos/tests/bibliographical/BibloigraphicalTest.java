package me.manzhos.tests.bibliographical;

import io.restassured.response.Response;
import me.manzhos.base.BaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BibloigraphicalTest extends BaseTest {

    @Test
    public void bibliographicalTest() throws IOException {
        Response response = given().contentType("application/json")
                .when().log().all()
                .get("http://library.rijksmuseum.nl:9998/biblios?version=1.1&operation=searchRetrieve&maximumRecords=2&query=Boucher");
        response.then().log().all();
        response.then().statusCode(200);

    }
}
