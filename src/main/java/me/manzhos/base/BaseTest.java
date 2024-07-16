package me.manzhos.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import me.manzhos.utils.PropertiesReader;

import java.io.IOException;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class BaseTest {

    private String apiKey;
    private PropertiesReader propertiesReader;

    static AllureRestAssured allureFilter;

    public RequestSpecification mainSpecification(String culture) throws IOException {

        allureFilter = new AllureRestAssured()
                .setRequestAttachmentName("Request")
                .setResponseAttachmentName("Response");

        this.propertiesReader = new PropertiesReader();
        try {
            this.apiKey = System.getProperty(apiKey);
        }
        catch (NullPointerException ex){
            this.apiKey = propertiesReader.getValueFromConfig("api_key");
        }

        return  given()
                .filter(allureFilter)
                .contentType(ContentType.JSON)
                .pathParam("culture", culture)
                .queryParam("key", apiKey);
    }
}
