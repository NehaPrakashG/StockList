package com.example.stocklist.Networking;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

    public interface Apiservice {

        @GET("/api/stocks/{id}")
        Observable<Response<ResponseBody>> getResponse(@Path("id") String id);

    }


