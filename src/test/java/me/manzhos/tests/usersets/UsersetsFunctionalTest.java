package me.manzhos.tests.usersets;

import io.restassured.response.Response;
import me.manzhos.api.UsersetsApi;
import me.manzhos.base.BaseTest;
import me.manzhos.base.RetryConfig;
import me.manzhos.dataproviders.PaginationDataProvider;
import me.manzhos.models.AllUsersetsResponse;
import me.manzhos.models.SpecificUsersetResponse;
import me.manzhos.utils.BOMRemover;
import me.manzhos.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class UsersetsFunctionalTest extends BaseTest{

    private String baseUrl;
    private PropertiesReader propertiesReader;
    private UsersetsApi usersetsApi;
    private BOMRemover bomRemover;
    private SpecificUsersetResponse specificUsersetResponse;
    private AllUsersetsResponse allUsersetsResponse;

    @BeforeMethod (alwaysRun = true)
    public void getEnvironmentUrl() throws IOException {
        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("usersetUrl");
        usersetsApi = new UsersetsApi();
        bomRemover = new BOMRemover();
        specificUsersetResponse = new SpecificUsersetResponse();
        allUsersetsResponse = new AllUsersetsResponse();
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkAllUsersetsHappyPathTest() throws IOException {
        String allUsersets = usersetsApi.getAllUsersetsApi(baseUrl, "nl").then().extract().asString();
        AllUsersetsResponse cleanedResponseBody = bomRemover.removeBOM(allUsersetsResponse, allUsersets);

        Assert.assertEquals(cleanedResponseBody.getUserSets().size(), 10);
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkAllUsersetsHappyPathEnTest() throws IOException {
        String allUsersets = usersetsApi.getAllUsersetsApi(baseUrl, "en").then().extract().asString();
        AllUsersetsResponse cleanedResponseBody = bomRemover.removeBOM(allUsersetsResponse, allUsersets);

        Assert.assertEquals(cleanedResponseBody.getUserSets().size(), 10);
    }

    @Test(dataProviderClass = PaginationDataProvider.class, dataProvider = "pagination", retryAnalyzer = RetryConfig.class)
    public void checkAllUsersetsPagination(String page, String usersetsPerPage, int expectedAmount) throws IOException {
        String allUsersets = usersetsApi.getAllUsersetsWithPaginationApi(baseUrl, "nl", page, usersetsPerPage)
                .then().extract().asString();
        AllUsersetsResponse cleanedResponseBody = bomRemover.removeBOM(allUsersetsResponse, allUsersets);

        int sizePerPage = Integer.valueOf(cleanedResponseBody.getUserSets().size());
        Assert.assertEquals(sizePerPage, expectedAmount);
    }

    @Test(retryAnalyzer = RetryConfig.class)
    public void checkSpecificUsersetTest() throws IOException {
        String userset = "1664319-mijn-eerste-verzameling";
        String stringWithBom = usersetsApi.getUsersetsApi(baseUrl, userset).then().extract().asString();
        SpecificUsersetResponse cleanedResponseBody = bomRemover.removeBOM(specificUsersetResponse, stringWithBom);

        Assert.assertEquals(cleanedResponseBody.getUserSet().getId(), userset);
    }

    // this test is disabled as the response contains ZWNBSP (zero width no-break space ) character
    // that prevents it from being parsed
    @Test(enabled = false)
    public void checkSpecificUsersetTestFailed() throws IOException {
        String userset = "1664319-mijn-eerste-verzameling";
        Response specificUsersetResponse= usersetsApi.getUsersetsApi(baseUrl, userset);
        specificUsersetResponse.then().log().all();
        specificUsersetResponse.then().statusCode(200).body("userSer.id", equalTo(userset));
    }
}
