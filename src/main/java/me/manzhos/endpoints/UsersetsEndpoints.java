package me.manzhos.endpoints;

public interface UsersetsEndpoints {

    String getAllUsersets = "/api/{culture}/usersets";
    String getUsersetDetails = "/api/{culture}/usersets/{set-id}";
}
