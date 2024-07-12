package me.manzhos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

public class SpecificUsersetModel {

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Links {
        @JsonProperty("overview")
        private String overview;

        @JsonProperty("web")
        private String web;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        @JsonProperty("id")
        private int id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("lang")
        private String lang;

        @JsonProperty("avatarUrl")
        private String avatarUrl;

        @JsonProperty("headerUrl")
        private String headerUrl;

        @JsonProperty("initials")
        private String initials;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserSet {
        @JsonProperty("links")
        private Links links;

        @JsonProperty("id")
        private String id;

        @JsonProperty("count")
        private int count;

        @JsonProperty("type")
        private String type;

        @JsonProperty("name")
        private String name;

        @JsonProperty("slug")
        private String slug;

        @JsonProperty("description")
        private String description;

        @JsonProperty("user")
        private User user;

        @JsonProperty("setItems")
        private List<Object> setItems;

        @JsonProperty("createdOn")
        private String createdOn;

        @JsonProperty("updatedOn")
        private String updatedOn;
    }
}
