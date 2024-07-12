package me.manzhos.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import me.manzhos.utils.PropertiesReader;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BaseTest {

    private String apiKey;

    public RequestSpecification mainSpecification(String culture) throws IOException {

        apiKey = new PropertiesReader().getValueFromConfig("api_key");

        return  given().contentType(ContentType.JSON).pathParam("culture", culture)
              .queryParam("key", apiKey);
    }


}
