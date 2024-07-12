package me.manzhos.dataproviders;

import org.testng.annotations.DataProvider;

public class PaginationDataProvider {

    @DataProvider(name = "pagination")
    public static Object[][] paginationData() {
        return new Object[][]{
                {"1", "1", "1"}
        };
    }
}