package me.manzhos.utils;

import java.util.List;

public class CollectionsApiUtil {

    public static boolean isListSortedAlphabetically(List<String> values, boolean ascending) {
        for (int i = 1; i < values.size(); i++) {
            if (ascending) {
                if (values.get(i - 1).compareTo(values.get(i)) > 0) {
                    return false;
                }
            } else {
                if (values.get(i - 1).compareTo(values.get(i)) < 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
