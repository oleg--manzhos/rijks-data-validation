package me.manzhos.tests.bibliographical;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import me.manzhos.base.BaseTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
@Feature("Bibliographic data")
public class BibloigraphicalTest extends BaseTest {

    @Description("Check biblographical endpoint returns data")
    @Test(testName = "Bibliographical")
    public void bibliographicalTest() throws IOException {
        Response response = given().contentType(ContentType.JSON)
                .when().log().all()
                .get("http://library.rijksmuseum.nl:9998/biblios?version=1.1&operation=searchRetrieve&maximumRecords=2&query=Boucher");
        response.then().log().all();
        response.then().statusCode(200);

    }
}
