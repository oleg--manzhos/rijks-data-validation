package me.manzhos.dataproviders;

import org.testng.annotations.DataProvider;

public class PaginationDataProvider {

    @DataProvider(name = "pagination")
    public static Object[][] paginationData() {
        return new Object[][]{
                {"0", "0", 0},
                {"0", "1", 1},
                {"1", "1", 1},
                {"2", "10", 10},
                {"3", "100", 100},
                {"0", "1500", 1500},
                {"1500", "0", 0}
        };
    }

    @DataProvider(name = "negative-pagination")
    public static Object[][] negativePaginationData() {
        return new Object[][]{
                {"a", "0", 0},
                //all the data rows below look not correct
                {"0", "a", 10},
                {"1", "+", 10},
                {"+", "10", 10},
        };
    }
}