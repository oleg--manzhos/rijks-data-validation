package me.manzhos.enums;

public enum SortByEnum {

    RELEVANCE("relevance"),
    OBJECT_TYPE("objecttype"),
    CHRONOLOGIC("chronologic"),
    ACHRONOLOGIC("achronologic"),
    ARTIST("artist"),
    ARTIST_DESC("artistdesc");

    private final String value;

    SortByEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
