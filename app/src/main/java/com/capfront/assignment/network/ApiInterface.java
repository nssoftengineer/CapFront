package com.capfront.assignment.network;


import com.capfront.assignment.models.Data;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by neeraj on 10/01/2019.
 */

public interface ApiInterface {
    @GET("chou4")
    Call<Data> getProduct();
}
