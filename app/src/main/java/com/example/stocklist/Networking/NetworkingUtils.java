package com.example.stocklist.Networking;

public class NetworkingUtils {
    private static Apiservice userService;

    public static Apiservice getUserApiInstance() {
        if (userService == null)
            userService = APIRetrofitAdapter.getInstance().create(Apiservice.class);

        return userService;
    }
}
