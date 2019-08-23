package com.capfront.assignment.viewmodel;

import com.capfront.assignment.models.Content;
import com.capfront.assignment.models.Data;
import com.capfront.assignment.network.RestApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by neeraj on 10/01/2019.
 */


public class ProductViewModel extends Observable {
    private ArrayList<Content> rowData;


    public ProductViewModel() {
        rowData = new ArrayList<>();
    }

    public ArrayList<Content> getRowData() {
        return rowData;
    }

    public void bindData() {
        Call<Data> call = RestApi.getApiInterface().getProduct();
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Data data = response.body();
                List<Content> contents=data.getContent();

                notifiedDataChanges(contents);
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    //update data and notify observer
    private void notifiedDataChanges(List<Content> rowData) {
        getRowData().clear();
        getRowData().addAll(rowData);
        setChanged();
        notifyObservers();
    }
}
