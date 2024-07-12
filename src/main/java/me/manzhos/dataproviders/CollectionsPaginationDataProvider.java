package me.manzhos.dataproviders;

import org.testng.annotations.DataProvider;

public class CollectionsPaginationDataProvider {

    @DataProvider(name = "pagination")
    public static Object[][] collectionsPaginationData() {
        return new Object[][]{
                {"0", "0", 10},
                {"0", "1", 1},
                {"1", "1", 1},
                {"2", "10", 10},
                {"10", "99", 99},
                {"3", "100", 100},
                {"22", "101", 10},
                {"0", "1500", 100},
                {"1500", "0", 0}
        };
    }
}