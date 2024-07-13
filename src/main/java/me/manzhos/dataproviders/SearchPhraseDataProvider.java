package me.manzhos.dataproviders;

import org.testng.annotations.DataProvider;

public class SearchPhraseDataProvider {
    @DataProvider(name = "search-term")
    public static Object[][] collectionsSearchData() {
        return new Object[][]{
                {"Lodewijk Schelfhout"},
                {"Hilversum"},
                {"RP-T-1915-74"},
                {"BK-KOG-656"},
                {"Isabella van Bourbon"}
        };
    }
}
