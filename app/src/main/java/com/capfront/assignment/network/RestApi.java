package com.capfront.assignment.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by neeraj on 10/01/2019.
 */


public class RestApi {

    public static final String BASE_URL = "https://api.myjson.com/bins/";
    private static Retrofit retrofit;

    public static ApiInterface getApiInterface() {
        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }

}
