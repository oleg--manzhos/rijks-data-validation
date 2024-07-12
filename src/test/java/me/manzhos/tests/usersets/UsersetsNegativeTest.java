package me.manzhos.tests.usersets;

import io.restassured.response.Response;
import me.manzhos.api.UsersetsApi;
import me.manzhos.base.BaseTest;
import me.manzhos.dataproviders.PaginationDataProvider;
import me.manzhos.models.AllUsersetsResponse;
import me.manzhos.models.SpecificUsersetResponse;
import me.manzhos.utils.BOMRemover;
import me.manzhos.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class UsersetsNegativeTest extends BaseTest {

    private String baseUrl;
    private PropertiesReader propertiesReader;
    private UsersetsApi usersetsApi;
    private BOMRemover bomRemover;
    private SpecificUsersetResponse specificUsersetResponse;
    private AllUsersetsResponse allUsersetsResponse;

    @BeforeMethod
    public void getEnvironmentUrl() throws IOException {
        propertiesReader = new PropertiesReader();
        baseUrl = propertiesReader.getValueFromConfig("usersetUrl");
        usersetsApi = new UsersetsApi();
        bomRemover = new BOMRemover();
        specificUsersetResponse = new SpecificUsersetResponse();
        allUsersetsResponse = new AllUsersetsResponse();
    }

    @Test(dataProviderClass = PaginationDataProvider.class, dataProvider = "negative-pagination" )
    public void checkUsersetsResultsNegative(String page, String usersetsPerPage, int expectedAmount) throws IOException {
        String allUsersets = usersetsApi.getAllUsersetsWithPaginationApi(baseUrl, "nl", page, usersetsPerPage)
                .then().extract().asString();
        AllUsersetsResponse cleanedResponseBody = bomRemover.removeBOM(allUsersetsResponse, allUsersets);

        Assert.assertEquals(cleanedResponseBody.getUserSets().size(), expectedAmount);
    }

    @Test
    public void checkUsersetsResultsNonExistingCulture() throws IOException {
        Response allUsersets = usersetsApi.getAllUsersetsApi(baseUrl, "dk");
        allUsersets.then().log().all();
        allUsersets.then().statusCode(404);
    }

    @Test
    public void checkUsersetsResultsInvalidCulture() throws IOException {
        Response allUsersets = usersetsApi.getAllUsersetsApi(baseUrl, "++");
        allUsersets.then().log().all();
        allUsersets.then().statusCode(404);
    }

    @Test
    public void checkUnexistingSpecificUsersetTest() throws IOException {
        String userset = "3664319-mijn-eerste-verzameling";
        Response usersetResponse = usersetsApi.getUsersetsApi(baseUrl, userset);
        usersetResponse.then().log().all();
        usersetResponse.then().statusCode(403);
    }
}
