package me.manzhos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AllUsersetsModel {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Links {
        private String self;
        private String web;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private int id;
        private String name;
        private String lang;
        private String avatarUrl;
        private String headerUrl;
        private String initials;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserSet {
        private Links links;
        private String id;
        private int count;
        private String type;
        private String name;
        private String slug;
        private String description;
        private User user;

        @JsonProperty("createdOn")
        private String createdOn;

        @JsonProperty("updatedOn")
        private String updatedOn;
    }
}
