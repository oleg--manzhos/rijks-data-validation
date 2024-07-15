package me.manzhos.base;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import me.manzhos.utils.PropertiesReader;

import java.io.IOException;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class BaseTest {

    private String apiKey;
    private PropertiesReader propertiesReader;

    public RequestSpecification mainSpecification(String culture) throws IOException {
        this.propertiesReader = new PropertiesReader();
        try {
            this.apiKey = System.getProperty(apiKey);
        }
        catch (NullPointerException ex){
            apiKey = propertiesReader.getValueFromConfig("api_key");
        }
        System.out.println("API key is: -----> " + apiKey);
        return  given().contentType(ContentType.JSON).pathParam("culture", culture).queryParam("key", apiKey);
    }
}
