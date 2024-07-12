package me.manzhos.endpoints;

public interface CollectionsEndpoint {

    String getAllCollections = "api/{culture}/collection";
    String getCollectionDetails = "/api/{culture}/collection/{object-number}";
    String getTiles = "api/{culture}/collection/{object-number}/tiles";

}
